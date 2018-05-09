package hr.fer.zemris.optjava.dz3.neighborhoods;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;

public class DoubleArrayNormNeighborhood implements INeighborhood<DoubleArraySolution> {
	public double[] deltas;
	public Random random;

	public DoubleArrayNormNeighborhood(double[] deltas) {
		super();
		this.deltas = deltas;
		random = new Random();
	}

	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution neighbor) {
		// mijenjamo jednu varijablu
		int i = random.nextInt(deltas.length);
		double[] values = Arrays.copyOf(neighbor.values, neighbor.values.length);
		values[i] += random.nextGaussian() * deltas[i];

		return new DoubleArraySolution(values);
	}

}
