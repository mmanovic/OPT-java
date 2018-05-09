package hr.fer.zemris.optjava.dz5.part1.models;

import java.util.BitSet;
import java.util.Random;

public class Mutation {
	private Random random = new Random();
	private double mutationProbability;

	public Mutation(double mutationProbability) {
		super();
		this.mutationProbability = mutationProbability;
	}

	public void mutate(Chromosome x) {
		BitSet set = x.getBitSet();
		int n = set.size();
		for (int i = 0; i < n; i++) {
			if (random.nextDouble() <= mutationProbability) {
				set.flip(i);
			}
		}
		x.revalidateFitness();
	}
}
