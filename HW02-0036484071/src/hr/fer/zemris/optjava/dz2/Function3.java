package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class Function3 implements IHFunction {
	private Matrix m;
	private static double EPSILON = 0.001;

	public Function3(Matrix m) {
		super();
		this.m = m;
	}

	@Override
	public int getNumberOfVariables() {
		return m.getRowDimension();
	}

	@Override
	public double getValueAt(Matrix matrix) {
		checkMatrixArgument(matrix);
		int n = getNumberOfVariables();
		double result = 0;
		for (int i = 0; i < n; i++) {
			double tmp = 0;
			for (int j = 0; j < n; j++) {
				tmp += m.get(i, j) * matrix.get(j, 0);
			}
			tmp -= m.get(i, n);
			result += tmp * tmp;
		}
		return result;
	}

	@Override
	public Matrix getValueOfGradientAt(Matrix matrix) {
		checkMatrixArgument(matrix);
		int n = this.getNumberOfVariables();
		Matrix gradient = new Matrix(n, 1);

		for (int i = 0; i < n; i++) {
			Matrix delta = new Matrix(matrix.getArray());
			delta.set(i, 0, matrix.get(i, 0) + EPSILON);
			double maxValue = this.getValueAt(delta);
			delta.set(i, 0, matrix.get(i, 0) - EPSILON);
			double minValue = this.getValueAt(delta);
			gradient.set(i, 0, (maxValue - minValue) / (2 * EPSILON));
		}
		return gradient.times(1.0 / (gradient.normF()));
		// int n = getNumberOfVariables();
		// double[][] values = new double[n][1];
		// for (int k = 0; k < n; k++) {
		// double tmp1 = 0;
		// for (int i = 0; i < n; i++) {
		// double tmp2 = 0;
		// for (int j = 0; j < n; j++) {
		// tmp2 += m.get(i, j) * matrix.get(i, 0);
		// }
		// tmp2 -= m.get(i, n);
		// tmp1 += tmp2 * 2 * m.get(i, k);
		// }
		// values[k][0] = tmp1;
		// }
		//
		// Matrix result = new Matrix(values, n, 1);
		// return result.times(1.0 / result.normF());
	}

	@Override
	public Matrix getHesseMatrix(Matrix matrix) {
		int n = getNumberOfVariables();
		double[][] result = new double[n][n];

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++) {
					result[i][j] += 2 * m.get(k, i) * m.get(k, j);
				}
			}
		}
		Matrix hesse = new Matrix(result, n, n);
		return hesse;
	}

	private void checkMatrixArgument(Matrix matrix) {
		if (matrix.getColumnDimension() != 1 || matrix.getRowDimension() != getNumberOfVariables()) {
			throw new IllegalArgumentException("Matrix must be " + getNumberOfVariables() + "*1 dimension.");
		}
	}

}
