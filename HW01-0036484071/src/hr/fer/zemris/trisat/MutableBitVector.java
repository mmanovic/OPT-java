package hr.fer.zemris.trisat;

public class MutableBitVector extends BitVector {
	public MutableBitVector(boolean... bits) {
		super(bits);
	}

	public MutableBitVector(int n) {
		super(n);
	}

	// zapisuje predanu vrijednost u zadanu varijablu
	public void set(int index, boolean value) {
		vector[index] = value;
	}

	public void increment() {
		int index = 0;
		while (index < vector.length - 1 && vector[index]) {
			vector[index++] = false;
		}
		vector[index] = true;
	}
}
