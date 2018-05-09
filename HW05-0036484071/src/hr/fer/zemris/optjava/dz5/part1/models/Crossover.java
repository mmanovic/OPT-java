package hr.fer.zemris.optjava.dz5.part1.models;

import java.util.BitSet;
import java.util.Random;

public class Crossover {
	private Random random = new Random();

	public Crossover() {
		super();
	}

	public Chromosome cross(Chromosome x, Chromosome y) {
		BitSet xSet = x.getBitSet();
		BitSet ySet = y.getBitSet();
		int n = x.getLength();
		int rand1 = random.nextInt(n);
		int rand2 = random.nextInt(n);
		int crossPoint1 = Integer.min(rand1, rand2);
		int crossPoint2 = Integer.max(rand1, rand2);

		BitSet childSet = new BitSet(n);
		for (int i = crossPoint1; i <= crossPoint2; i++) {
			childSet.set(i, ySet.get(i));
		}
		for (int i = 0; i < crossPoint1; i++) {
			childSet.set(i, xSet.get(i));
		}
		for (int i = crossPoint2 + 1; i < n; i++) {
			childSet.set(i, xSet.get(i));
		}

		return new Chromosome(x.getFunction(), childSet, n);

	}
}
