package hr.fer.zemris.optjava.dz8.algorithm;

import java.util.Arrays;
import java.util.Random;

public class DoubleArraySolution implements Comparable<DoubleArraySolution> {
	public static double wMin = -1;
	public static double wMax = 1;
	public double[] values;

	private double error;
	private static Random random = new Random();

	public DoubleArraySolution(int size) {
		super();
		this.values = new double[size];
		randomize();
	}

	public DoubleArraySolution(double[] values) {
		super();
		this.values = values;
	}

	public static DoubleArraySolution crossover(DoubleArraySolution target, DoubleArraySolution mutant, double Cr) {
		int dimension = target.values.length;
		double[] childValues = new double[dimension];
		boolean isMutantCompAdded = false;
		for (int i = 0; i < dimension; i++) {
			if (random.nextDouble() <= Cr) {
				childValues[i] = target.values[i];
			} else {
				childValues[i] = mutant.values[i];
				isMutantCompAdded = true;
			}
		}
		if (!isMutantCompAdded) {
			int randomInt = random.nextInt(dimension);
			childValues[randomInt] = mutant.values[randomInt];
		}
		return new DoubleArraySolution(childValues);
	}

	

	@Override
	public int compareTo(DoubleArraySolution o) {
		return Double.compare(this.error, o.error);
	}

	public double getError() {
		return error;
	}

	public void setError(double error) {
		this.error = error;
	}

	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}

	public DoubleArraySolution duplicate() {
		return new DoubleArraySolution(Arrays.copyOf(values, values.length));
	}

	public void randomize() {
		int n = values.length;
		for (int i = 0; i < n; i++) {
			values[i] = wMin + random.nextDouble() * (wMax - wMin);
		}
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("[");
		for (double value : values) {
			string.append(value + " ");
		}
		string.append("]");
		return string.toString();
	}
}
