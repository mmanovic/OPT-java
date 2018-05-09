package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class Function1 implements IHFunction {

	@Override
	public int getNumberOfVariables() {
		return 2;
	}

	@Override
	public double getValueAt(Matrix matrix) {
		checkMatrixArgument(matrix);
		return Math.pow(matrix.get(0, 0), 2) + Math.pow(matrix.get(1, 0) - 1, 2);
	}

	@Override
	public Matrix getValueOfGradientAt(Matrix matrix) {
		checkMatrixArgument(matrix);
		double[][] values = new double[2][1];
		values[0][0] = 2 * matrix.get(0, 0);
		values[1][0] = 2 * (matrix.get(1, 0) - 1);
		return new Matrix(values, 2, 1);
	}

	@Override
	public Matrix getHesseMatrix(Matrix matrix) {
		checkMatrixArgument(matrix);
		double[][] values = new double[2][2];
		values[0][0] = 2;
		values[0][1] = 0;
		values[1][0] = 0;
		values[1][1] = 2;

		return new Matrix(values, 2, 2);
	}

	private void checkMatrixArgument(Matrix matrix) {
		if (matrix.getColumnDimension() != 1 || matrix.getRowDimension() != 2) {
			throw new IllegalArgumentException("Matrix must be 2*1 dimension.");
		}
	}
}
