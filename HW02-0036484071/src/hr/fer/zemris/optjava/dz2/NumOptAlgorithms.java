package hr.fer.zemris.optjava.dz2;

import Jama.Matrix;

public class NumOptAlgorithms {
	public static double GRADIENT_EPSILON = 0.01;
	public static double LAMBDA_EPSILON = 0.0001;

	public static Matrix descentGradientAlgorithm(IFunction function, int maxIterations, Matrix x0, boolean print) {

		int i = 0;
		while (i < maxIterations) {
			Matrix d = function.getValueOfGradientAt(x0).times(-1);
			if (d.normF() < GRADIENT_EPSILON) {
				break;
			}
			if (print) {
				printMatrix(x0);
			}

			double maxLambda = getLambdaUpperBound(function, x0, d);
			double lambda = getLambda(function, x0, d, maxLambda);
			x0 = x0.plus(d.times(lambda));
			i++;
		}
		System.out.println("Final solution: ");
		printMatrix(x0);
		return x0;
	}

	private static double getLambda(IFunction function, Matrix x0, Matrix direction, double maxLambda) {
		double l = 0;
		double u = maxLambda;
		int iterations = 0;
		double med = 0;
		while (iterations++ < 100) {
			med = (u + l) / 2;
			double dTheta = function.getValueOfGradientAt(x0.plus(direction.times(med))).transpose().times(direction)
					.get(0, 0);
			if (Math.abs(dTheta) < LAMBDA_EPSILON) {
				return med;
			} else if (dTheta < 0) {
				l = med;
			} else if (dTheta > 0) {
				u = med;
			}
		}
		return med;
	}

	private static void printMatrix(Matrix matrix) {
		double[][] values = matrix.getArray();
		int length1 = values.length;
		int length2 = values[0].length;

		for (int j = 0; j < length2; j++) {
			for (int i = 0; i < length1; i++) {
				System.out.print(values[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static double getLambdaUpperBound(IFunction function, Matrix x0, Matrix direction) {
		double maxLambda = 1;
		while (function.getValueOfGradientAt(x0.plus(direction.times(maxLambda))).transpose().times(direction).get(0,
				0) < 0) {
			maxLambda *= 2;
		}
		return maxLambda;
	}

	public static Matrix newtonAlgorithm(IHFunction function, int maxIterations, Matrix x0, boolean print) {
		int i = 0;
		while (i < maxIterations) {
			Matrix d = function.getValueOfGradientAt(x0).times(-1);
			if (d.normF() < GRADIENT_EPSILON) {
				break;
			}
			if (print) {
				printMatrix(x0);
			}
			d = function.getHesseMatrix(x0).inverse().times(d);
			double maxLambda = getLambdaUpperBound(function, x0, d);
			double lambda = getLambda(function, x0, d, maxLambda);
			x0 = x0.plus(d.times(lambda));
			i++;
		}
		System.out.println("Final solution: ");
		printMatrix(x0);
		return x0;
	}
}
