package hr.fer.zemris.optjava.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.optjava.operators.Chromosome;
import hr.fer.zemris.optjava.operators.ICrossover;
import hr.fer.zemris.optjava.operators.IMutation;
import hr.fer.zemris.optjava.operators.ISelection;

public class NSGA {
	private int MAX_GENERATION = 1500;
	private List<Chromosome> population;
	private IMutation mutation;
	private ICrossover crossover;
	private ISelection selection;
	private int populationSize;
	private ShareValueCalculator calc;
	public static double REDUCTION_FACTOR = 0.9;

	public NSGA(int maxIterations, List<Chromosome> population, IMutation mutation, ICrossover crossover,
			ISelection selection, ShareValueCalculator calc) {
		super();
		MAX_GENERATION = maxIterations;
		this.population = population;
		populationSize = population.size();
		this.mutation = mutation;
		this.crossover = crossover;
		this.selection = selection;
		this.calc = calc;
	}

	public List<Chromosome> run() {
		int generation = 0;
		List<List<Chromosome>> fronts = DominationSort.getFronts(population);
		evaluatePopulation(fronts);
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
			fronts = DominationSort.getFronts(newPopulation);
			evaluatePopulation(fronts);
			newPopulation.sort(Comparator.reverseOrder());
			population = newPopulation;
			generation++;
		}

		return population;
	}

	public void evaluatePopulation(List<List<Chromosome>> fronts) {
		double minFitness = populationSize;
		for (List<Chromosome> front : fronts) {
			double frontMinFitness = Double.MAX_VALUE;
			for (Chromosome x : front) {
				double niche = 0;
				for (Chromosome other : front) {
					niche += calc.getShareValue(x, other);
				}
				x.setFitness(minFitness / niche);
				frontMinFitness = Double.min(frontMinFitness, x.getFitness());
			}
			minFitness = frontMinFitness * REDUCTION_FACTOR;
		}
	}
}
