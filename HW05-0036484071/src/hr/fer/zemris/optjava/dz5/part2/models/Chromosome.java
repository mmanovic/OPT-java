package hr.fer.zemris.optjava.dz5.part2.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz5.part2.models.Chromosome;

public class Chromosome implements Comparable<Chromosome> {
	private int[] distribution;
	private Function function;
	private double fitness;

	public Chromosome(Function function, int n) {
		distribution = new int[n];
		randomize(new Random());
		this.function = function;
		this.fitness = function.valueAt(this);
	}

	public Chromosome(Function function, int[] distribution) {
		this.distribution = distribution;
		this.function = function;
		fitness = function.valueAt(this);
	}

	public int[] getDistribution() {
		return distribution;
	}

	public void setDistribution(int[] distribution) {
		this.distribution = distribution;
		revalidateFitness();
	}

	public Function getFunction() {
		return function;
	}

	public double getFitness() {
		return fitness;
	}

	public void revalidateFitness() {
		fitness = function.valueAt(this);
	}

	public void randomize(Random random) {
		int n = distribution.length;
		List<Integer> tmp = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			tmp.add(i);
		}
		Collections.shuffle(tmp);
		int i = 0;
		for (Integer number : tmp) {
			distribution[i++] = number;
		}
	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(fitness, o.fitness);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(distribution);
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
		if (!Arrays.equals(distribution, other.distribution))
			return false;
		return true;
	}

	@Override
	public String toString() {
		int n = distribution.length;
		StringBuilder string = new StringBuilder();
		string.append("[");
		for (int i = 0; i < n; i++) {
			string.append(distribution[i] + " ");
		}
		string.deleteCharAt(string.length() - 1);
		string.append("]");
		return string.toString();
	}

}
