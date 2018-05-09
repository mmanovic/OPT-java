package hr.fer.zemris.optjava.dz5.part1.models;

import java.util.BitSet;

public class Function {

	public Function() {
		super();
	}

	public double valueAt(Chromosome x) {
		BitSet set = x.getBitSet();
		int n = x.getLength();
		int k = 0;
		for (int i = 0; i < n; i++) {
			if (set.get(i)) {
				k++;
			}
		}
		if (k <= 0.8 * n) {
			return (double) k / (double) n;
		} else if (k <= 0.9 * n) {
			return 0.8;
		} else {
			return (double) (2 * k) / (double) n - 1.0;
		}
	}
}
