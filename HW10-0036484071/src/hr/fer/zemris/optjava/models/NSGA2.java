package hr.fer.zemris.optjava.models;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.optjava.operators.Chromosome;
import hr.fer.zemris.optjava.operators.ICrossover;
import hr.fer.zemris.optjava.operators.IMutation;
import hr.fer.zemris.optjava.operators.ISelection;

public class NSGA2 {
	private int MAX_GENERATION = 100;
	private List<Chromosome> population;
	private IMutation mutation;
	private ICrossover crossover;
	private ISelection selection;
	private int populationSize;

	public NSGA2(int maxIterations, List<Chromosome> population, IMutation mutation, ICrossover crossover,
			ISelection selection) {
		super();
		MAX_GENERATION = maxIterations;
		this.population = population;
		populationSize = population.size();
		this.mutation = mutation;
		this.crossover = crossover;
		this.selection = selection;
	}

	public List<Chromosome> run() {
		int generation = 0;
		List<List<Chromosome>> fronts = SortsUtil.dominationSort(population);
		SortsUtil.crowdingSort(fronts);
		while (generation < MAX_GENERATION) {
			List<Chromosome> newPopulation = new ArrayList<>();

			while (newPopulation.size() < populationSize) {
				Chromosome x = selection.select(population);
				Chromosome y = selection.select(population);
				Chromosome child = mutation.mutate(crossover.cross(x, y));
				child.clipping();
				child.getProblem().evaluateSolution(child.getValues(), child.getObjectives());
				newPopulation.add(child);
			}
			newPopulation.addAll(population);
			fronts = SortsUtil.dominationSort(newPopulation);
			fronts = SortsUtil.crowdingSort(fronts);
			population = new ArrayList<>();
			for (List<Chromosome> front : fronts) {
				if (population.size() == populationSize) {
					break;
				}
				if (front.size() <= populationSize - population.size()) {
					population.addAll(front);
					continue;
				}
				int rest = populationSize - population.size();
				for (int i = 0; i < rest; i++) {
					population.add(front.get(i));
				}
			}
			generation++;
		}

		return population;
	}

}
