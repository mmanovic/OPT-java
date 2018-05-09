package hr.fer.zemris.optjava.threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IGAEvaluator;
import hr.fer.zemris.optjava.rng.EVOThread;

public class ThreadPool<T> {
	private GASolution<T> RED_PILL;
	private BlockingQueue<GASolution<T>> tasks;
	private List<Thread> workers = new ArrayList<>();

	public ThreadPool(int n, BlockingQueue<GASolution<T>> tasks, BlockingQueue<GASolution<T>> results,
			IGAEvaluator<T> evaluator, GASolution<T> RED_PILL) {
		this.tasks = tasks;
		this.RED_PILL = RED_PILL;
		for (int i = 0; i < n; ++i) {
			workers.add(new EVOThread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							GASolution<T> solution = tasks.take();
							if (solution.getData() == null) {
								break;
							}
							evaluator.evaluate(solution);
							results.put(solution);
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
