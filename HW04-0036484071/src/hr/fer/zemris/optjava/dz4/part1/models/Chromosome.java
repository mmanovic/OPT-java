package hr.fer.zemris.optjava.dz4.part1.models;

import java.util.Random;

public class Chromosome implements Comparable<Chromosome> {
	public static double INIT_MIN = -10;
	public static double INIT_MAX = 10;
	private Function function;
	private double[] values;
	private double fitness;

	public Chromosome(Function function, double[] values) {
		super();
		this.function = function;
		this.values = values;
		fitness = -function.valueAt(this);
	}

	public Chromosome(Function function, int n) {
		this.function = function;
		this.values = new double[n];
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			values[i] = random.nextDouble() * (INIT_MAX - INIT_MIN) + INIT_MIN;
		}
		fitness = -function.valueAt(this);
	}

	public void setValue(int index, double value) {
		values[index] = value;
		fitness = function.valueAt(this);
	}

	public double getValue(int index) {
		return values[index];
	}

	public Function getFunction() {
		return function;
	}

	public double[] getValues() {
		return values;
	}

	public double getFitness() {
		return fitness;
	}

	public int getLength() {
		return values.length;
	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(fitness, o.fitness);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("[ ");
		int n = getLength();
		for (int i = 0; i < n; i++) {
			string.append(values[i]+" ");
		}
		string.append("]");
		return string.toString();
	}

}
