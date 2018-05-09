package hr.fer.zemris.optjava.dz4.part1.models;

import java.util.Random;

public class Mutation {
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
		return new Chromosome(x.getFunction(), values);
	}
}
