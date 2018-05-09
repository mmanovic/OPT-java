package hr.fer.zemris.optjava.dz2;

import java.util.List;
import static java.lang.Math.exp;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.pow;
import Jama.Matrix;

public class Function4 implements IHFunction {
	private Matrix m;
	private static double EPSILON = 0.0001;

	public Function4(Matrix m) {
		super();
		this.m = m;
	}

	@Override
	public int getNumberOfVariables() {
		return 6;
	}

	@Override
	public double getValueAt(Matrix matrix) {
		checkMatrixArgument(matrix);
		double result = 0;

		double a = matrix.get(0, 0);
		double b = matrix.get(1, 0);
		double c = matrix.get(2, 0);
		double d = matrix.get(3, 0);
		double e = matrix.get(4, 0);
		double f = matrix.get(5, 0);
		int row = m.getRowDimension();
		for (int i = 0; i < row; i++) {
			double difference = 0;
			double x1 = m.get(i, 0);
			double x2 = m.get(i, 1);
			double x3 = m.get(i, 2);
			double x4 = m.get(i, 3);
			double x5 = m.get(i, 4);
			double y = m.get(i, 5);
			difference += a * x1;
			difference += b * x1 * x1 * x1 * x2;
			difference += c * exp(d * x3) * (1 + cos(e * x4));
			difference += f * x4 * x5 * x5;

			difference -= y;

			result += difference * difference;
		}
		return result;/////////
	}

	@Override
	public Matrix getValueOfGradientAt(Matrix matrix) {
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
	}

	@Override
	public Matrix getHesseMatrix(Matrix matrix) {
		checkMatrixArgument(matrix);
		double[][] rez = new double[getNumberOfVariables()][getNumberOfVariables()];
		double a = matrix.get(0, 0);
		double b = matrix.get(1, 0);
		double c = matrix.get(2, 0);
		double d = matrix.get(3, 0);
		double e = matrix.get(4, 0);
		double f = matrix.get(5, 0);
		int row = m.getRowDimension();
		for (int i = 0; i < row; i++) {
			double x1 = m.get(i, 0);
			double x2 = m.get(i, 1);
			double x3 = m.get(i, 2);
			double x4 = m.get(i, 3);
			double x5 = m.get(i, 4);
			double y = m.get(i, 5);

			rez[0][0] += 2 * pow(x1, 2);
			rez[0][1] += 2 * pow(x1, 4) * x2;
			rez[0][2] += 2 * x1 * exp(d * x3) * (cos(e * x4) + 1);
			rez[0][3] += 2 * c * x1 * x3 * exp(d * x3) * (cos(e * x4) + 1);
			rez[0][4] += -2 * c * x1 * x4 * exp(d * x3) * sin(e * x4);
			rez[0][5] += 2 * x1 * x4 * pow(x5, 2);

			rez[1][0] += 2 * pow(x1, 4) * x2;
			rez[1][1] += 2 * pow(x1, 6) * pow(x2, 2);
			rez[1][2] += 2 * pow(x1, 3) * x2 * exp(d * x3) * (cos(e * x4) + 1);
			rez[1][3] += 2 * c * pow(x1, 3) * x2 * x3 * exp(d * x3) * (cos(e * x4) + 1);
			rez[1][4] += -2 * c * pow(x1, 3) * x2 * x4 * exp(d * x3) * sin(e * x4);
			rez[1][5] += 2 * pow(x1, 3) * x2 * x4 * pow(x5, 2);

			rez[2][0] += 2 * x1 * exp(d * x3) * (cos(e * x4) + 1);
			rez[2][1] += 2 * pow(x1, 3) * x2 * exp(d * x3) * (cos(e * x4) + 1);
			rez[2][2] += 2 * exp(2 * d * x3) * pow((cos(e * x4) + 1), 2);
			rez[2][3] += 2 * c * x3 * exp(2 * d * x3) * pow((cos(e * x4) + 1), 2) + 2 * x3 * exp(d * x3)
					* (cos(e * x4) + 1)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1));
			rez[2][4] += -2 * x4 * exp(d * x3) * sin(e * x4)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1))
					- 2 * c * x4 * exp(2 * d * x3) * sin(e * x4) * (cos(e * x4) + 1);
			rez[2][5] += 2 * x4 * pow(x5, 2) * exp(d * x3) * (cos(e * x4) + 1);

			rez[3][0] += 2 * c * x1 * x3 * exp(d * x3) * (cos(e * x4) + 1);
			rez[3][1] += 2 * c * pow(x1, 3) * x2 * x3 * exp(d * x3) * (cos(e * x4) + 1);
			rez[3][2] += 2 * c * x3 * exp(2 * d * x3) * pow((cos(e * x4) + 1), 2) + 2 * x3 * exp(d * x3)
					* (cos(e * x4) + 1)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1));
			rez[3][3] += 2 * c * c * pow(x3, 2) * exp(2 * d * x3) * pow((cos(e * x4) + 1), 2) + 2 * c * pow(x3, 2)
					* exp(d * x3) * (cos(e * x4) + 1)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1));
			rez[3][4] += -2 * c * c * x3 * x4 * exp(2 * d * x3) * sin(e * x4) * (cos(e * x4) + 1) - 2 * c * x3 * x4
					* exp(d * x3) * sin(e * x4)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1));
			rez[3][5] += 2 * c * x3 * x4 * pow(x5, 2) * exp(d * x3) * (cos(e * x4) + 1);

			rez[4][0] += -2 * c * x1 * x4 * exp(d * x3) * sin(e * x4);
			rez[4][1] += -2 * c * pow(x1, 3) * x2 * x4 * exp(d * x3) * sin(e * x4);
			rez[4][2] += -2 * x4 * exp(d * x3) * sin(e * x4)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1))
					- 2 * c * x4 * exp(2 * d * x3) * sin(e * x4) * (cos(e * x4) + 1);
			rez[4][3] += -2 * c * c * x3 * x4 * exp(2 * d * x3) * sin(e * x4) * (cos(e * x4) + 1) - 2 * c * x3 * x4
					* exp(d * x3) * sin(e * x4)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1));
			rez[4][4] += 2 * c * c * pow(x4, 2) * exp(2 * d * x3) * pow(sin(e * x4), 2) - 2 * c * pow(x4, 2)
					* exp(d * x3) * cos(e * x4)
					* (b * x2 * pow(x1, 3) + a * x1 + f * x4 * pow(x5, 2) - y + c * exp(d * x3) * (cos(e * x4) + 1));
			rez[4][5] += -2 * c * pow(x4, 2) * pow(x5, 2) * exp(d * x3) * sin(e * x4);

			rez[5][0] += 2 * x1 * x4 * pow(x5, 2);
			rez[5][1] += 2 * pow(x1, 3) * x2 * x4 * pow(x5, 2);
			rez[5][2] += 2 * x4 * pow(x5, 2) * exp(d * x3) * (cos(e * x4) + 1);
			rez[5][3] += 2 * c * x3 * x4 * pow(x5, 2) * exp(d * x3) * (cos(e * x4) + 1);
			rez[5][4] += -2 * c * pow(x4, 2) * pow(x5, 2) * exp(d * x3) * sin(e * x4);
			rez[5][5] += 2 * pow(x4, 2) * pow(x5, 4);
		}
		return new Matrix(rez, 6, 6);

	}

	private void checkMatrixArgument(Matrix matrix) {
		if (matrix.getColumnDimension() != 1 || matrix.getRowDimension() != getNumberOfVariables()) {
			throw new IllegalArgumentException("Matrix must be " + getNumberOfVariables() + "*1 dimension.");
		}
	}

}
