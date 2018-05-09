package hr.fer.zemris.optjava.operators;

import java.util.Random;

import hr.fer.zemris.optjava.models.MOOPProblem;

public class Chromosome implements Comparable<Chromosome> {
	public static double[] mins;
	public static double[] maxs;
	private MOOPProblem problem;
	private double[] values;
	private double[] objectives;
	private double fitness;
	private double distance;
	private int rank;

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public Chromosome(MOOPProblem problem, double[] values, int objectivesSize) {
		super();
		this.problem = problem;
		this.values = values;
		objectives = new double[objectivesSize];
	}

	public Chromosome(MOOPProblem problem, int n, int objectivesSize) {
		this.problem = problem;
		this.values = new double[n];
		objectives = new double[objectivesSize];
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			values[i] = random.nextDouble() * (maxs[i] - mins[i]) + mins[i];
		}
	}

	public boolean dominateOver(Chromosome other) {
		boolean domination = false;
		for (int i = 0; i < values.length; i++) {
			if (objectives[i] > other.objectives[i]) {
				return false;
			} else if (objectives[i] < other.objectives[i]) {
				domination = true;
			}
		}
		return domination;
	}

	public void clipping() {
		for (int i = 0; i < values.length; i++) {
			values[i] = Double.max(values[i], mins[i]);
			values[i] = Double.min(values[i], maxs[i]);
		}
	}

	public double[] getObjectives() {
		return objectives;
	}

	public int getObjectivesSize() {
		return objectives.length;
	}

	public void setObjectives(double[] objectives) {
		this.objectives = objectives;
	}

	public double getValue(int index) {
		return values[index];
	}

	public MOOPProblem getProblem() {
		return problem;
	}

	public void setProblem(MOOPProblem problem) {
		this.problem = problem;
	}

	public double[] getValues() {
		return values;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
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
		string.append("Objective values: [ ");
		int n = objectives.length;
		for (int i = 0; i < n; i++) {
			string.append(objectives[i] + " ");
		}
		string.append("] Solution values: ");

		string.append("[ ");
		n = values.length;
		for (int i = 0; i < n; i++) {
			string.append(values[i] + " ");
		}
		string.append("]");
		return string.toString();
	}

}
