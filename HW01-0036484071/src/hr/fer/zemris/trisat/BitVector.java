package hr.fer.zemris.trisat;

import java.util.Arrays;
import java.util.Random;

public class BitVector {
	protected boolean[] vector;

	public BitVector(Random rand, int numberOfBits) {
		vector = new boolean[numberOfBits];
		for (int i = 0; i < numberOfBits; i++) {
			vector[i] = rand.nextBoolean();
		}

	}

	public BitVector(boolean... bits) {
		vector = bits;
	}

	public BitVector(int n) {
		vector = new boolean[n];
	}

	// vraća vrijednost index-te varijable
	public boolean get(int index) {
		if (index >= 0 && index < getSize()) {
			return vector[index];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}

	}

	// vraća broj varijabli koje predstavlja
	public int getSize() {
		return vector.length;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		int length = getSize();
		for (int i = 0; i < length; i++) {
			if (vector[i]) {
				string.append("1");
			} else {
				string.append("0");
			}
		}
		return string.toString();
	}

	// vraća promjenjivu kopiju trenutnog rješenja
	public MutableBitVector copy() {
		return new MutableBitVector(Arrays.copyOfRange(vector, 0, getSize()));
	}

}
