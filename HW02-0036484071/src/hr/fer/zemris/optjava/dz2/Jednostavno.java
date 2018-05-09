package hr.fer.zemris.optjava.dz2;

import java.util.Random;

import Jama.Matrix;

public class Jednostavno {

	public static void main(String[] args) {
		if (args.length != 2 && args.length != 4) {
			System.out.println("Invalid number of arguments.");
			return;
		}

		int maxIterations = Integer.parseInt(args[1]);
		double[][] values = new double[2][1];
		if (args.length == 4) {
			values[0][0] = Double.parseDouble(args[2]);
			values[1][0] = Double.parseDouble(args[3]);
		} else {
			Random random = new Random();
			values[0][0] = random.nextDouble() * 10 - 5.0;
			values[1][0] = random.nextDouble() * 10 - 5.0;
		}
		Matrix x0 = new Matrix(values, 2, 1);
		IHFunction function;
		if (args[0].equals("1a")) {
			function = new Function1();
			NumOptAlgorithms.descentGradientAlgorithm(function, maxIterations, x0, true);
		} else if (args[0].equals("2a")) {
			function = new Function2();
			NumOptAlgorithms.descentGradientAlgorithm(function, maxIterations, x0, true);
		} else if (args[0].equals("1b")) {
			function = new Function1();
			NumOptAlgorithms.newtonAlgorithm(function, maxIterations, x0, true);
		} else if (args[0].equals("2b")) {
			function = new Function2();
			NumOptAlgorithms.newtonAlgorithm(function, maxIterations, x0, true);
		}

	}

}
