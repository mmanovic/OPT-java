package hr.fer.zemris.optjava.dz4.part2.models;

import java.util.List;
import java.util.Random;

public class TournamentWorstSelection implements ISelection {
	private Random random = new Random();
	private int m;

	public TournamentWorstSelection(int m) {
		super();
		if (m < 2) {
			throw new IllegalArgumentException("Parameter n must be greater than two.");
		}
		this.m = m;
	}

	@Override
	public Chromosome select(List<Chromosome> population) {
		double worstFitness = Double.MAX_VALUE;
		int indexOfBest = -1;
		int size = population.size();
		for (int i = 0; i < m; i++) {
			int index = random.nextInt(size);
			double fitness = population.get(index).getFitness();
			if (fitness < worstFitness) {
				worstFitness = fitness;
				indexOfBest = index;
			}
		}
		return population.get(indexOfBest);
	}

}
