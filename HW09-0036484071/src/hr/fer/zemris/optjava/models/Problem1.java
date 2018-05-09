package hr.fer.zemris.optjava.models;

public class Problem1 implements MOOPProblem {

	@Override
	public int getNumberOfObjectives() {
		return 4;
	}

	@Override
	public void evaluateSolution(double[] solution, double[] objectives) {
		if (solution.length != 4 || objectives.length != 4) {
			throw new IllegalArgumentException("Arguments for first problem are invalid.");
		}
		for (int i = 0; i < 4; i++) {
			objectives[i] = solution[i] * solution[i];
		}

	}

	@Override
	public double[] evaluateSolution(double[] solution) {
		double[] objectives = new double[4];
		evaluateSolution(solution, objectives);
		return objectives;
	}

	@Override
	public double[] getMins() {
		return new double[] { -5, -5, -5, -5 };
	}

	@Override
	public double[] getMaxs() {
		return new double[] { 5, 5, 5, 5 };
	}

	@Override
	public int getSolutionDimension() {
		return 4;
	}

}
