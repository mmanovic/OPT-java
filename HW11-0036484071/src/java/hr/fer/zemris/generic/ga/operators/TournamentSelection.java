package hr.fer.zemris.generic.ga.operators;

import java.util.List;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class TournamentSelection implements ISelection<int[]> {
	private int n;

	public TournamentSelection(int n) {
		super();
		if (n < 2) {
			throw new IllegalArgumentException("Parameter n must be greater than two.");
		}
		this.n = n;
	}

	@Override
	public GASolution<int[]> select(List<GASolution<int[]>> population) {
		IRNG random = RNG.getRNG();
		double bestFitness = -Double.MAX_VALUE;
		int indexOfBest = -1;
		int size = population.size();
		for (int i = 0; i < n; i++) {
			int index = random.nextInt(0, size);
			double fitness = population.get(index).fitness;
			if (fitness > bestFitness) {
				bestFitness = fitness;
				indexOfBest = index;
			}
		}
		return population.get(indexOfBest);
	}

}
