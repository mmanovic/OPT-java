package hr.fer.zemris.optjava.models;

public interface MOOPProblem {
	public int getNumberOfObjectives();
	
	public int getSolutionDimension();

	public void evaluateSolution(double[] solution, double[] objectives);

	public double[] evaluateSolution(double[] solution);

	public double[] getMins();

	public double[] getMaxs();
}
