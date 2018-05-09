package hr.fer.zemris.optjava.dz4.part2.models;

import java.util.List;

public class Chromosome implements Comparable<Chromosome> {
	private List<Bin> bins;
	private Function function;
	private double fitness;
	private int maxHeight;

	public Chromosome(Function function, List<Bin> bins) {
		super();
		this.function = function;
		this.bins = bins;
		fitness = function.valueAt(this);
		maxHeight = bins.get(0).getMaxHeight();
	}

	public void addSticks(List<Stick> sticks) {
		for (Stick stick : sticks) {
			boolean added = false;
			for (Bin bin : bins) {
				if (bin.addStick(stick)) {
					added = true;
					break;
				}
			}
			if (!added) {
				Bin bin = new Bin(maxHeight);
				bin.addStick(stick);
				bins.add(bin);
			}
		}
		fitness = function.valueAt(this);
	}

	public Function getFunction() {
		return function;
	}

	public double getFitness() {
		return fitness;
	}

	public List<Bin> getBins() {
		return bins;
	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(fitness, o.fitness);
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Bin bin : bins) {
			string.append(bin.toString() + ", ");
		}
		return string.substring(0, string.length() - 2).toString();
	}

}
