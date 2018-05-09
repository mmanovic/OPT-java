package hr.fer.zemris.optjava.dz3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz3.algorithms.IOptAlgorithm;
import hr.fer.zemris.optjava.dz3.algorithms.SimulatedAnnealing;
import hr.fer.zemris.optjava.dz3.decoders.GrayBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoders.IDecoder;
import hr.fer.zemris.optjava.dz3.decoders.NaturalBinaryDecoder;
import hr.fer.zemris.optjava.dz3.decoders.PassThroughDecoder;
import hr.fer.zemris.optjava.dz3.functions.Function;
import hr.fer.zemris.optjava.dz3.functions.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhoods.BitVectorNeighborhood;
import hr.fer.zemris.optjava.dz3.neighborhoods.DoubleArrayUnifNeighborhood;
import hr.fer.zemris.optjava.dz3.neighborhoods.INeighborhood;
import hr.fer.zemris.optjava.dz3.schedules.GeometricTempSchedule;
import hr.fer.zemris.optjava.dz3.schedules.ITempSchedules;
import hr.fer.zemris.optjava.dz3.solutions.BitVectorSolution;
import hr.fer.zemris.optjava.dz3.solutions.DoubleArraySolution;

public class RegresijaSustava {
	private static double MIN = -10;
	private static double MAX = 10;
	private static double DELTA = 1;

	private static double ALPHA = 0.98;
	private static double TEMP = 1500;
	private static int INNER_LIMIT = 1000;
	private static int OUTER_LIMIT = 1000;

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Invalid number of arguments.");
			return;
		}
		Path file = Paths.get(args[0]);
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
		IFunction function = getFunction(lines);
		if (args[1].equalsIgnoreCase("decimal")) {
			solveWithDecimalSolution(function);
		} else if (args[1].startsWith("binary:")) {
			String[] tmp = args[1].split(":");
			int nOfBits = Integer.parseInt(tmp[1]);
			if (nOfBits < 5 || nOfBits > 30) {
				System.out.println("Invalid number of bits.");
				System.exit(0);
			}
			solveWithBitVectorSolution(function, nOfBits);
		} else {
			System.out.println("Second argument is invalid.");
		}
	}

	private static void solveWithBitVectorSolution(IFunction function, int nOfBits) {
		IDecoder<BitVectorSolution> decoder = new GrayBinaryDecoder(MIN, MAX, nOfBits, function.numberOfVariables());
		// IDecoder<BitVectorSolution> decoder = new NaturalBinaryDecoder(MIN,
		// MAX, nOfBits, function.numberOfVariables());
		INeighborhood<BitVectorSolution> neighborhood = new BitVectorNeighborhood();
		BitVectorSolution startSolution = new BitVectorSolution(function.numberOfVariables() * nOfBits);
		startSolution.randomize(new Random());
		ITempSchedules schedule = new GeometricTempSchedule(ALPHA, TEMP, INNER_LIMIT, OUTER_LIMIT);
		IOptAlgorithm<BitVectorSolution> algorithm = new SimulatedAnnealing<BitVectorSolution>(decoder, function, true,
				neighborhood, startSolution, schedule);
		algorithm.run();
	}

	private static void solveWithDecimalSolution(IFunction function) {
		IDecoder<DoubleArraySolution> decoder = new PassThroughDecoder();
		int n = function.numberOfVariables();
		double[] deltas = new double[n];
		double[] mins = new double[n];
		double[] maxs = new double[n];
		for (int i = 0; i < n; i++) {
			deltas[i] = DELTA;
			mins[i] = MIN;
			maxs[i] = MAX;
		}
		INeighborhood<DoubleArraySolution> neighborhood = new DoubleArrayUnifNeighborhood(deltas);
		DoubleArraySolution startSolution = new DoubleArraySolution(function.numberOfVariables());
		startSolution.randomize(new Random(), mins, maxs);
		// IOptAlgorithm<DoubleArraySolution> algorithm = new
		// GreedyAlgorithm<DoubleArraySolution>(decoder, function, true,
		// neighborhood, startSolution);
		ITempSchedules schedule = new GeometricTempSchedule(ALPHA, TEMP, INNER_LIMIT, OUTER_LIMIT);
		IOptAlgorithm<DoubleArraySolution> algorithm = new SimulatedAnnealing<DoubleArraySolution>(decoder, function,
				true, neighborhood, startSolution, schedule);
		algorithm.run();
	}

	private static IFunction getFunction(List<String> lines) {
		int x = 0, y = 0;
		for (String line : lines) {
			if (!line.startsWith("#")) {
				y = line.split(",\\s+").length;
				x++;
			}
		}
		double[][] m = new double[x][y];
		int i = 0;
		for (String line : lines) {
			if (line.startsWith("#")) {
				continue;
			}
			String[] tmp = line.substring(1, line.length() - 1).split(",\\s+");
			int length = tmp.length;
			for (int j = 0; j < length; j++) {
				m[i][j] = Double.parseDouble(tmp[j]);
			}
			i++;
		}
		return new Function(m);
	}
}
