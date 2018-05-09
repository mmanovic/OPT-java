package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public interface IFunction {
	public int getNumberOfVariables();
	public double getValueAt(Matrix matrix);
	public Matrix getValueOfGradientAt(Matrix matrix);
}
