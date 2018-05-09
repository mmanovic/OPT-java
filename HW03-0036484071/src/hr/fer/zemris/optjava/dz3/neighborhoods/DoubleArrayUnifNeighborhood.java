package hr.fer.zemris.optjava.dz3.neighborhoods;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;

public class DoubleArrayUnifNeighborhood implements INeighborhood<DoubleArraySolution> {
	public double[] deltas;
	public Random random;

	public DoubleArrayUnifNeighborhood(double[] deltas) {
		super();
		this.deltas = deltas;
		random = new Random();
	}

	@Override
	public DoubleArraySolution randomNeighbor(DoubleArraySolution neighbor) {
		// mijenjamo jednu varijablu
		int i = random.nextInt(deltas.length);
		double[] values = Arrays.copyOf(neighbor.values, neighbor.values.length);

		values[i] += random.nextDouble() * deltas[i] * 2 - deltas[i];

		return new DoubleArraySolution(values);
	}

}
