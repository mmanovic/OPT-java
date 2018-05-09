package hr.fer.zemris.optjava.dz3.solutions;

import java.util.Arrays;
import java.util.Random;

public class DoubleArraySolution extends SingleObjectiveSolution {
	public double[] values;

	public DoubleArraySolution(int size) {
		super();
		this.values = new double[size];
	}

	public DoubleArraySolution(double[] values) {
		super();
		this.values = values;
	}

	public DoubleArraySolution newLikeThis() {
		return new DoubleArraySolution(values.length);
	}

	public DoubleArraySolution duplicate() {
		return new DoubleArraySolution(Arrays.copyOf(values, values.length));
	}

	public void randomize(Random random, double[] min, double[] max) {
		int n = values.length;
		for (int i = 0; i < n; i++) {
			values[i] = min[i] + random.nextDouble() * (max[i] - min[i]);
		}
	}

}
