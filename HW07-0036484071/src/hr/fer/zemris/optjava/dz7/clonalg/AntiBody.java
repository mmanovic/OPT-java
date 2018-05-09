package hr.fer.zemris.optjava.dz7.clonalg;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.models.IFunction;

public class AntiBody implements Comparable<AntiBody> {
	public static double X_MIN = -1;
	public static double X_MAX = 1;
	private IFunction function;
	private double[] values;
	private double error;

	private Random random = new Random();
	private int dimension;

	public AntiBody(IFunction function, int dimension) {
		this.function = function;
		this.dimension = dimension;
		this.values = new double[dimension];
		randomize();
		error = function.valueAt(values);
	}

	private AntiBody(IFunction function, int dimension, double[] values, double error) {
		this.function = function;
		this.dimension = dimension;
		this.values = values;
		this.error = error;
	}

	public AntiBody clone() {
		double[] copyValues = Arrays.copyOf(values, dimension);
		return new AntiBody(function, dimension, copyValues, error);
	}

	public void mutate(double mutationRate) {
		for (int i = 0; i < values.length; i++) {
			if (random.nextDouble() < mutationRate) {
				values[i] += random.nextGaussian();
			}
		}
		error = function.valueAt(values);
	}

	public double[] getValues() {
		return values;
	}

	public double getError() {
		return error;
	}

	private void randomize() {
		for (int i = 0; i < dimension; i++) {
			values[i] = X_MIN + random.nextDouble() * (X_MAX - X_MIN);
		}
	}

	@Override
	public int compareTo(AntiBody a) {
		return Double.compare(error, a.getError());
	}
}
