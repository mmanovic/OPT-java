package hr.fer.zemris.optjava.operators;

import java.util.Random;

public class Crossover implements ICrossover {
	private Random random = new Random();
	private double alpha;

	public Crossover(double alpha) {
		super();
		this.alpha = alpha;
	}

	// BLX krizanje
	public Chromosome cross(Chromosome x, Chromosome y) {
		int size = x.getLength();
		double[] values = new double[size];
		for (int i = 0; i < size; i++) {
			double cmin = Double.min(x.getValue(i), y.getValue(i));
			double cmax = Double.max(x.getValue(i), y.getValue(i));
			double interval = alpha * (cmax - cmin);
			values[i] = random.nextDouble() * (cmax - cmin + 2 * interval) + cmin - interval;
		}
		return new Chromosome(x.getProblem(), values,x.getObjectives().length);
	}
}
