package hr.fer.zemris.generic.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.generic.ga.operators.ICrossover;
import hr.fer.zemris.generic.ga.operators.IMutation;
import hr.fer.zemris.generic.ga.operators.ISelection;
import hr.fer.zemris.optjava.threads.ThreadPool2;

public class GenerationAlgorithm2<T> {
	private static int N_OF_CHILDREN_PER_THREAD = 3;
	private int maxGenerations;
	private double minFitness;
	private int populationSize;
	private IGAEvaluator<T> evaluator;
	private List<GASolution<T>> population;

	private ISelection<T> selection;
	private ICrossover<T>[] crossovers;
	private IMutation<T>[] mutations;

	private BlockingQueue<Integer> tasks = new LinkedBlockingQueue<>();
	private BlockingQueue<List<GASolution<T>>> results = new LinkedBlockingQueue<>();

	public GenerationAlgorithm2(int maxGenerations, double minFitness, List<GASolution<T>> population,
			IGAEvaluator<T> evaluator, ISelection<T> selection, ICrossover<T>[] crossovers, IMutation<T>[] mutations) {
		super();
		this.population = population;
		this.maxGenerations = maxGenerations;
		this.minFitness = minFitness;
		this.populationSize = population.size();
		this.evaluator = evaluator;
		this.selection = selection;
		this.crossovers = crossovers;
		this.mutations = mutations;

	}

	public GASolution<T> run() throws InterruptedException {
		for (GASolution<T> unit : population) {
			evaluator.evaluate(unit);
		}
		int n = Runtime.getRuntime().availableProcessors();
		ThreadPool2<T> pool = new ThreadPool2(n, tasks, results, evaluator, population, selection, crossovers,
				mutations);

		GASolution<T> best = Collections.min(population);

		int generation = 0;
		while (generation < maxGenerations && -best.fitness > minFitness) {
			generation++;
			List<GASolution<T>> newPopulation = new ArrayList<>();
			newPopulation.add(best);

			try {
				int numberOfTasks = (populationSize - 1) / N_OF_CHILDREN_PER_THREAD;
				for (int i = 0; i < numberOfTasks; i++) {
					tasks.put(N_OF_CHILDREN_PER_THREAD);
				}
				tasks.put((populationSize - 1) % N_OF_CHILDREN_PER_THREAD);
				numberOfTasks++;

				for (int i = 0; i < numberOfTasks; i++) {
					List<GASolution<T>> result = results.take();
					newPopulation.addAll(result);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			population.clear();
			population.addAll(newPopulation);
			best = Collections.min(population);

			if (generation % 10 == 0) {
				System.out.println("Generation: " + generation + " Fitness: " + -best.fitness);
			}
		}
		pool.shutdown();
		System.out.println("Final best fitness: " + -best.fitness);
		return best;
	}

}
