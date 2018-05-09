package hr.fer.zemris.optjava.dz3.neighborhoods;

import java.util.Arrays;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;

public class BitVectorNeighborhood implements INeighborhood<BitVectorSolution> {
	Random random = new Random();

	@Override
	public BitVectorSolution randomNeighbor(BitVectorSolution neighbor) {
		byte[] bits = Arrays.copyOf(neighbor.bits, neighbor.bits.length);
		int i = random.nextInt(bits.length);
		bits[i] ^= 1;
		int j = random.nextInt(bits.length);
		bits[i] ^= j;
		return new BitVectorSolution(bits);
	}

}
