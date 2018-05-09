package hr.fer.zemris.optjava.models;

public class Problem2 implements MOOPProblem {

	@Override
	public int getNumberOfObjectives() {
		return 2;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		if (solution.length != 2 || objectives.length != 2) {
			throw new IllegalArgumentException("Arguments for first problem are invalid.");
		}
		objectives[0] = solution[0];
		objectives[1] = (1.0 + solution[1]) / solution[0];
	}

	@Override
	public double[] evaluateSolution(double[] solution) {
		double[] objectives = new double[4];
		evaluateSolution(solution, objectives);
		return objectives;
	}

	@Override
	public double[] getMins() {
		return new double[] { 0.1, 0 };
	}

	@Override
	public double[] getMaxs() {
		return new double[] { 1, 5 };

	}

	@Override
	public int getSolutionDimension() {
		return 2;
	}

}
