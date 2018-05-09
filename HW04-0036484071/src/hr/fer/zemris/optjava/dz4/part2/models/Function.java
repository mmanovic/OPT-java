package hr.fer.zemris.optjava.dz4.part2.models;

import java.util.List;

public class Function {
	private double k;// eksponent kojim potenciramo postotak popunjenosti

	public Function(double k) {
		super();
		this.k = k;
	}

	public double valueAt(Chromosome x) {
		List<Bin> bins = x.getBins();
		double value = 0;
		for (Bin bin : bins) {
			value += Math.pow(1 - (double) bin.getRestSpace() / (double) bin.getMaxHeight(), k);
		}
		return value / bins.size();
	}
}
