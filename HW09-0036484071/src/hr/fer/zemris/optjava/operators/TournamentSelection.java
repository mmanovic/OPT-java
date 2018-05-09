package hr.fer.zemris.optjava.operators;

import java.util.List;
import java.util.Random;

public class TournamentSelection implements ISelection {
	private Random random = new Random();
	private int n;

	public TournamentSelection(int n) {
		super();
		if (n < 2) {
			throw new IllegalArgumentException("Parameter n must be greater than two.");
		}
		this.n = n;
	}

	@Override
	public Chromosome select(List<Chromosome> population) {
		double bestFitness = -Double.MAX_VALUE;
		int indexOfBest = -1;
		int size = population.size();
		for (int i = 0; i < n; i++) {
			int index = random.nextInt(size);
			double fitness = population.get(index).getFitness();
			if (fitness > bestFitness) {
				bestFitness = fitness;
				indexOfBest = index;
			}
		}
		return population.get(indexOfBest);
	}

}
