package hr.fer.zemris.optjava.operators;

import java.util.Random;

public class Mutation implements IMutation {
	private double sigma;
	private Random random = new Random();

	public Mutation(double sigma) {
		super();
		this.sigma = sigma;
	}

	public Chromosome mutate(Chromosome x) {
		double[] values = x.getValues();
		int n = x.getLength();
		for (int i = 0; i < n; i++) {
			values[i] += random.nextGaussian() * sigma;
		}
		return new Chromosome(x.getProblem(), values, x.getObjectives().length);
	}
}
