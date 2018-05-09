package hr.fer.zemris.optjava.dz3.solutions;

public class SingleObjectiveSolution {
	private double fitness;
	private double value;

	public int compareTo(SingleObjectiveSolution solution) {
		return Double.compare(value, solution.value);
	}
}
