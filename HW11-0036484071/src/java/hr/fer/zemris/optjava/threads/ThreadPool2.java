package hr.fer.zemris.optjava.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.generic.ga.operators.ICrossover;
import hr.fer.zemris.generic.ga.operators.IMutation;
import hr.fer.zemris.generic.ga.operators.ISelection;
import hr.fer.zemris.optjava.rng.EVOThread;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class ThreadPool2<T> {
	private Integer RED_PILL = -1;
	private BlockingQueue<Integer> tasks;
	private List<Thread> workers = new ArrayList<>();

	public ThreadPool2(int n, BlockingQueue<Integer> tasks, BlockingQueue<List<GASolution<T>>> results,
			IGAEvaluator<T> evaluator, List<GASolution<T>> population, ISelection<T> selection,
			ICrossover<T>[] crossovers, IMutation<T>[] mutations) {

		this.tasks = tasks;
		for (int i = 0; i < n; i++) {
			workers.add(new EVOThread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							Integer counter = tasks.take();
							if (counter < 0) {
								break;
							}
							List<GASolution<T>> newPopulation = new ArrayList<>(counter);
							IRNG random = RNG.getRNG();
							for (int i = 0; i < counter; i++) {
								GASolution<T> x = selection.select(population);
								GASolution<T> y = selection.select(population);

								GASolution<T> child = crossovers[random.nextInt(0, crossovers.length)].crossover(x, y);
								mutations[random.nextInt(0, mutations.length)].mutate(child);
								evaluator.evaluate(child);
								newPopulation.add(child);
							}
							results.put(newPopulation);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}));
		}
		for (Thread worker : workers) {
			worker.start();
		}

	}

	public void shutdown() throws InterruptedException {
		int n = workers.size();
		for (int i = 0; i < n; ++i) {
			tasks.put(RED_PILL);
		}
	}
}
