package hr.fer.zemris.optjava.dz3.decoders;

import java.util.Arrays;

import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;

public abstract class BitVectorDecoder implements IDecoder<BitVectorSolution> {
	protected double[] mins;
	protected double[] maxs;
	protected int[] bits;
	protected int n;
	protected int totalBits = 0;

	public BitVectorDecoder(double[] mins, double[] maxs, int[] bits, int n) {
		super();
		this.mins = mins;
		this.maxs = maxs;
		this.bits = bits;
		this.n = n;
		for (int size : bits) {
			totalBits += size;
		}
	}

	public BitVectorDecoder(double min, double max, int bit, int n) {
		mins = new double[n];
		maxs = new double[n];
		bits = new int[n];
		this.n = n;
		Arrays.fill(maxs, max);
		Arrays.fill(bits, bit);
		Arrays.fill(mins, min);
		totalBits = bit * n;
	}

	public int getTotalBits() {
		return this.totalBits;
	}

	public int getDimensions() {
		return this.n;
	}


}
