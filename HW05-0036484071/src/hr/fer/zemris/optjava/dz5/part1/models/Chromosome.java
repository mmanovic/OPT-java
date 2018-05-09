package hr.fer.zemris.optjava.dz5.part1.models;

import java.util.BitSet;
import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {
	private BitSet bitSet;
	private Function function;
	private double fitness;
	private int length;

	public BitSet getBitSet() {
		return bitSet;
	}

	public double getFitness() {
		return fitness;
	}

	public Chromosome(Function function, int n) {
		this.length = n;
		this.function = function;
		bitSet = new BitSet(n);
		fitness = 0;
	}

	public int getLength() {
		return length;
	}

	public Function getFunction() {
		return function;
	}

	public Chromosome(Function function, BitSet set, int n) {
		this.length = n;
		this.function = function;
		bitSet = set;
		fitness = function.valueAt(this);
	}

	public void randomize(Random random) {
		for (int i = 0; i < length; i++) {
			bitSet.set(i, random.nextBoolean());
		}
		fitness = function.valueAt(this);
	}

	public void revalidateFitness() {
		fitness = function.valueAt(this);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int i = 0; i < length; i++) {
			if (bitSet.get(i)) {
				string.append("1");
			} else {
				string.append("0");
			}
		}
		return string.toString();

	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(fitness, o.fitness);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bitSet == null) ? 0 : bitSet.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Chromosome other = (Chromosome) obj;
		if (bitSet == null) {
			if (other.bitSet != null)
				return false;
		} else if (!bitSet.equals(other.bitSet))
			return false;
		return true;
	}

}
