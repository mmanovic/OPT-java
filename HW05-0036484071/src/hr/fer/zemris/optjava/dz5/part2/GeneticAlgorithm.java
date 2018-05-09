package hr.fer.zemris.optjava.dz5.part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part2.models.Chromosome;
import hr.fer.zemris.optjava.dz5.part2.models.Crossover;
import hr.fer.zemris.optjava.dz5.part2.models.Function;
import hr.fer.zemris.optjava.dz5.part2.models.ISelection;
import hr.fer.zemris.optjava.dz5.part2.models.Mutation;
import hr.fer.zemris.optjava.dz5.part2.models.TournamentSelection;

/**
 * Preporuèene konfiguracija za komandnu liniju: ./data/nug12.txt 60 20 Takoðer
 * vrijedi i za ostale datoteke.
 * 
 * @author Mato
 *
 */
public class GeneticAlgorithm {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Invalid number of arguments.");
			return;
		}
		int populationSize = Integer.parseInt(args[1]);
		int nOfSubpopulation = Integer.parseInt(args[2]);
		int tournamentSize = 3;

		Function function = new Function(args[0]);
		List<Chromosome> population = createPopulation(function, populationSize, function.getN());
		Crossover crossover = new Crossover();
		Mutation mutation = new Mutation();
		ISelection selection = new TournamentSelection(tournamentSize);

		for (int i = nOfSubpopulation; i > 0; i--) {
			int subPopulationSize = populationSize / i;
			List<Chromosome> newPopulation = new ArrayList<>();
			for (int j = 0; j < i; j++) {
				List<Chromosome> currentPopulation = new ArrayList<>();
				if (j == i - 1) {
					currentPopulation = population.subList(j * subPopulationSize, populationSize);
				} else {
					currentPopulation = population.subList(j * subPopulationSize, subPopulationSize * (j + 1));
				}
				newPopulation.addAll(offSpringAlgorithm(currentPopulation, crossover, mutation, selection));
			}
			population = newPopulation;
			System.out.println("====================================================================");
		}

		Chromosome currentBest = Collections.min(population);
		System.out.println("Final solution: " + currentBest + " Fitness: " + currentBest.getFitness());
	}

	public static int maxIterations = 500;
	public static double successRatio = 0.5;
	public static double compFactorStep = 2.0 / maxIterations;
	public static double compFactor = 0.2;
	public static double maxSelectionPressure = 300;

	public static List<Chromosome> offSpringAlgorithm(List<Chromosome> population, Crossover crossover,
			Mutation mutation, ISelection selection) {
		int iterations = 0;
		double actSelPressure = 1;
		int populationSize = population.size();
		while (iterations < maxIterations && actSelPressure < maxSelectionPressure) {
			Set<Chromosome> newPopulation = new HashSet<>();
			Set<Chromosome> pool = new HashSet<>();
			newPopulation.add(Collections.min(population));

			int poolSize = 0;
			while ((newPopulation.size() < successRatio * populationSize)
					&& (newPopulation.size() + poolSize < (populationSize * maxSelectionPressure))) {
				Chromosome x = selection.select(population);
				Chromosome y = selection.select(population);
				Chromosome child = crossover.cross(x, y);
				mutation.mutate(child);
				double betterFitness = Double.min(x.getFitness(), y.getFitness());
				double worseFitness = Double.max(x.getFitness(), y.getFitness());
				if (child.getFitness() < worseFitness - (worseFitness - betterFitness) * compFactor) {
					newPopulation.add(child);
				} else if (pool.size() < (1.0 - successRatio) * (double) populationSize - 1.0) {
					pool.add(child);
				}
				poolSize++;
			}

			actSelPressure = ((double) newPopulation.size() + poolSize) / populationSize;
			newPopulation.addAll(pool);
			while (newPopulation.size() < populationSize) {
				Chromosome x = selection.select(population);
				Chromosome y = selection.select(population);
				Chromosome child = crossover.cross(x, y);
				mutation.mutate(child);
				newPopulation.add(child);

			}
			population = new ArrayList<>(newPopulation);
			compFactor = Double.min(1, compFactor + compFactorStep);
			iterations++;

		}
		Chromosome currentBest = Collections.min(population);
		System.out.println("Fitness: " + currentBest.getFitness() + " " + currentBest);
		return population;
	}

	private static List<Chromosome> createPopulation(Function function, int populationSize, int n) {
		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			population.add(new Chromosome(function, n));
		}
		return population;
	}

}
