package hr.fer.zemris.generic.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import hr.fer.zemris.generic.ga.operators.ICrossover;
import hr.fer.zemris.generic.ga.operators.IMutation;
import hr.fer.zemris.generic.ga.operators.ISelection;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;
import hr.fer.zemris.optjava.threads.ThreadPool;

public class GenerationAlgorithm1<T> {
	private int maxGenerations;
	private double minFitness;
	private int populationSize;
	private IGAEvaluator<T> evaluator;
	private List<GASolution<T>> population;

	private ISelection<T> selection;
	private ICrossover<T>[] crossovers;
	private IMutation<T>[] mutations;
	private int crossoversLength;
	private int mutationsLength;

	private BlockingQueue<GASolution<T>> tasks = new LinkedBlockingQueue<>();
	private BlockingQueue<GASolution<T>> results = new LinkedBlockingQueue<>();

	public GenerationAlgorithm1(int maxGenerations, double minFitness, List<GASolution<T>> population,
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
		this.crossoversLength = crossovers.length;
		this.mutationsLength = mutations.length;

	}

	public GASolution<T> run() throws InterruptedException {
		int n = Runtime.getRuntime().availableProcessors();
		ThreadPool<T> pool = new ThreadPool(n, tasks, results, evaluator, new Chromosome());

		evaluate(population);
		GASolution<T> best = Collections.min(population);
		IRNG random = RNG.getRNG();

		int generation = 0;
		while (generation < maxGenerations && -best.fitness > minFitness) {
			generation++;
			List<GASolution<T>> newPopulation = new ArrayList<>();
			newPopulation.add(best);

			for (int i = 1; i < populationSize; i++) {
				GASolution<T> x = selection.select(population);
				GASolution<T> y = selection.select(population);

				// GASolution<T> child = crossovers[1].crossover(a, b);
				// mutations[0].mutate(child);
				GASolution<T> child = crossovers[random.nextInt(0, crossoversLength)].crossover(x, y);
				mutations[random.nextInt(0, mutationsLength)].mutate(child);

				newPopulation.add(child);
			}
			evaluate(newPopulation);

			population = newPopulation;
			best = Collections.min(population);

			if (generation % 10 == 0) {
				System.out.println("Generation: " + generation + " Fitness: " + -best.fitness);
			}
		}
		pool.shutdown();
		System.out.println("Final best fitness: " + -best.fitness);
		return best;
	}

	private void evaluate(List<GASolution<T>> population) throws InterruptedException {
		for (GASolution<T> solution : population) {
			tasks.put(solution);
		}
		for (int i = 0; i < populationSize; i++) {
			results.take();
		}

	}

	// private void evaluate(List<GASolution<T>> populationForEval) {
	// for (GASolution<T> unit : populationForEval) {
	// evaluator.evaluate(unit);
	// }
	// }

}
