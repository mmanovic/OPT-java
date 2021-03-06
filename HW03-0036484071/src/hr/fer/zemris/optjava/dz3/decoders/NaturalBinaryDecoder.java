package hr.fer.zemris.optjava.dz3.decoders;

import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;

public class NaturalBinaryDecoder extends BitVectorDecoder {

	public NaturalBinaryDecoder(double min, double max, int bit, int n) {
		super(min, max, bit, n);
		// TODO Auto-generated constructor stub
	}

	public NaturalBinaryDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super(mins, maxs, bits, n);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double[] decode(BitVectorSolution solution) {
		double[] values = new double[n];
		decode(solution, values);
		return values;
	}

	@Override
	public void decode(BitVectorSolution solution, double[] values) {
		byte[] bitVector = solution.bits;
		int i = 0;
		int component = 0;
		while (i < totalBits) {
			int length = bits[component];
			double sum = 0;
			for (int j = 0; j < length; j++) {
				sum += bitVector[i++] << (length - j - 1);
			}
			double range = maxs[component] - mins[component];
			double maxBinaryValue = Math.pow(2, length);
			values[component] = mins[component] + sum * range / maxBinaryValue;
			component++;
		}
	}

}
