package hr.fer.zemris.optjava.dz3.functions;

import static java.lang.Math.cos;
import static java.lang.Math.exp;

public class Function implements IFunction {
	double[][] values;

	public Function(double[][] values) {
		super();
		this.values = values;
	}

	@Override
	public double valueAt(double[] vector) {
		double result = 0;

		double a = vector[0];
		double b = vector[1];
		double c = vector[2];
		double d = vector[3];
		double e = vector[4];
		double f = vector[5];
		int row = values.length;
		for (int i = 0; i < row; i++) {
			double difference = 0;
			double x1 = values[i][0];
			double x2 = values[i][1];
			double x3 = values[i][2];
			double x4 = values[i][3];
			double x5 = values[i][4];
			double y = values[i][5];
			difference += a * x1;
			difference += b * x1 * x1 * x1 * x2;
			difference += c * exp(d * x3) * (1 + Math.cos(e * x4));
			difference += f * x4 * x5 * x5;

			difference -= y;

			result += difference * difference;
		}
		return result;
	}

	@Override
	public int numberOfVariables() {
		return 6;
	}

}
