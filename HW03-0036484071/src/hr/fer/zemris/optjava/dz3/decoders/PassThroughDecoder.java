package hr.fer.zemris.optjava.dz3.decoders;

import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;

public class PassThroughDecoder implements IDecoder<DoubleArraySolution> {

	@Override
	public double[] decode(DoubleArraySolution solution) {
		return solution.values;
	}

	@Override
	public void decode(DoubleArraySolution solution, double[] values) {
		System.arraycopy(solution.values, 0, values, 0, values.length);
	}

}
