package hr.fer.zemris.optjava.dz5.part1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;

import hr.fer.zemris.optjava.dz5.part1.models.Chromosome;
import hr.fer.zemris.optjava.dz5.part1.models.Crossover;
import hr.fer.zemris.optjava.dz5.part1.models.Function;
import hr.fer.zemris.optjava.dz5.part1.models.ISelection;
import hr.fer.zemris.optjava.dz5.part1.models.Mutation;
import hr.fer.zemris.optjava.dz5.part1.models.RandomSelection;
import hr.fer.zemris.optjava.dz5.part1.models.TournamentSelection;

/**
 * @author Mato
 *
 */
public class GeneticAlgorithm {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			return;
		}
		int n = Integer.parseInt(args[0]);
		int minPopulation = 20;
		int maxPopulation = 250;
		int maxEffort = maxPopulation * 50;
		int maxIterations = 50;
		double compFactorStep = 3.0 / maxIterations;
		double compFactor = 0.2;
		int tournamentSize = 5;
		double mutationProbability = 0.01;

		Function function = new Function();
		List<Chromosome> population = createPopulation(function, (minPopulation + maxPopulation) / 2, n);
		ISelection tournamentSelection = new TournamentSelection(tournamentSize);
		ISelection randomSelection = new RandomSelection();
		Crossover crossover = new Crossover();
		Mutation mutation = new Mutation(mutationProbability);
		int iteration = 0;
		Chromosome currentBest = null;
		while (iteration < maxIterations) {
			population.sort(Comparator.reverseOrder());
			currentBest = population.get(0);
			System.out.println("Population size: " + population.size() + " Fitness: " + currentBest.getFitness() + " "
					+ currentBest);
			Set<Chromosome> newPopulation = new HashSet<>();
			Set<Chromosome> pool = new HashSet<>();
			newPopulation.add(currentBest);
			int effort = 0;
			while (effort < maxEffort && newPopulation.size() < maxPopulation) {
				effort++;
				Chromosome x = tournamentSelection.select(population);
				Chromosome y = randomSelection.select(population);
				Chromosome child = crossover.cross(x, y);
				mutation.mutate(child);
				double betterFitness = Double.max(x.getFitness(), y.getFitness());
				double worseFitness = Double.min(x.getFitness(), y.getFitness());
				if (child.getFitness() > worseFitness + (betterFitness - worseFitness) * compFactor) {
					newPopulation.add(child);
				} else if (pool.size() < minPopulation) {
					pool.add(child);
				}
			}
			population.clear();
			population.addAll(newPopulation);
			Iterator<Chromosome> iterator = pool.iterator();
			while (population.size() < minPopulation) {
				population.add(iterator.next());
			}
			compFactor = Double.min(1, compFactor + compFactorStep);
			iteration++;
		}
		System.out.println("Final solution: " + currentBest + " fitness: " + currentBest.getFitness());
	}

	private static List<Chromosome> createPopulation(Function function, int populationSize, int n) {
		List<Chromosome> population = new ArrayList<>();
		Random random = new Random();
		for (int i = 0; i < populationSize; i++) {
			Chromosome x = new Chromosome(function, n);
			x.randomize(random);
			population.add(x);
		}
		return population;
	}
}
