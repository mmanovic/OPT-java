package hr.fer.zemris.optjava.dz2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import Jama.Matrix;

public class Sustav {

	public static void main(String[] args) {
		if (args.length != 3) {
			System.out.println("Invalid number of arguments.");
			return;
		}

		int maxIterations = Integer.parseInt(args[1]);
		Path file = Paths.get(args[2]);
		if (!Files.exists(file)) {
			System.out.println("File " + file.toString() + " doesn't exist.");
			return;
		}
		List<String> lines;
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Unable to open file.");
			return;
		}
		Matrix m = getMatrix(lines);
		IHFunction function = new Function3(m);
		int n = function.getNumberOfVariables();
		double[][] values = new double[n][1];
		Random random = new Random();
		for (int i = 0; i < n; i++) {
			values[i][0] = random.nextDouble() * 5.0;
		}
		// double value = function.getValueAt(new Matrix(values, 10, 1));
		if (args[0].equalsIgnoreCase("grad")) {
			System.out.println("Error value: " + function.getValueAt(NumOptAlgorithms.descentGradientAlgorithm(function,
					maxIterations, new Matrix(values, n, 1), true)));
		} else if (args[0].equalsIgnoreCase("newton")) {
			System.out.println("Error value: " + function.getValueAt(
					NumOptAlgorithms.newtonAlgorithm(function, maxIterations, new Matrix(values, n, 1), true)));
		}
	}

	private static Matrix getMatrix(List<String> lines) {
		int dimension = 0;
		for (String line : lines) {
			if (!line.startsWith("#")) {
				dimension++;
			}
		}
		Matrix m = new Matrix(dimension, dimension + 1);
		int i = 0;
		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}
			String[] tmp = line.substring(1, line.length() - 1).split(",\\s+");
			int length = tmp.length;
			for (int j = 0; j < length; j++) {
				m.set(i, j, Double.parseDouble(tmp[j]));
			}
			i++;
		}
		return m;
	}

}
