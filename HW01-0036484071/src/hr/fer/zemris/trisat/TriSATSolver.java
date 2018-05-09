package hr.fer.zemris.trisat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.zemris.trisat.algorithms.Algorithm1;
import hr.fer.zemris.trisat.algorithms.Algorithm2;
import hr.fer.zemris.trisat.algorithms.Algorithm3;
import hr.fer.zemris.trisat.algorithms.Algorithm4;
import hr.fer.zemris.trisat.algorithms.Algorithm5;
import hr.fer.zemris.trisat.algorithms.Algorithm6;
import hr.fer.zemris.trisat.algorithms.SATAlgorithm;

/**
 * @author Mato Manović
 *
 */
public class TriSATSolver {
	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("Program takes two parameters, number order of algorithm and path to file.");
			System.exit(1);
		}
		int noOfAlgorithm = Integer.parseInt(args[0]);
		Path path = Paths.get(args[1]);
		if (!path.toFile().exists()) {
			System.out.println("Path to file doesn't exist.");
			System.exit(1);
		}
		List<String> contentOfFile = new ArrayList<>();
		try {
			contentOfFile = Files.readAllLines(path);
		} catch (IOException e) {
			System.out.println("Problem with opening file.");
			System.exit(1);
		}

		SATFormula formula = createSATFormula(contentOfFile);
		System.out.println(formula);
		if (noOfAlgorithm == 1) {
			SATAlgorithm algorithm = new Algorithm1(formula);
			algorithm.perform();
		} else if (noOfAlgorithm == 2) {
			SATAlgorithm algorithm = new Algorithm2(formula);
			algorithm.perform();
		} else if (noOfAlgorithm == 3) {
			SATAlgorithm algorithm = new Algorithm3(formula);
			algorithm.perform();
		} else if (noOfAlgorithm == 4) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Give max iterations and max flips:");
			int maxIter = scanner.nextInt();
			int maxFlips = scanner.nextInt();
			SATAlgorithm algorithm = new Algorithm4(formula, maxIter, maxFlips);
			algorithm.perform();
		} else if (noOfAlgorithm == 5) {
			Scanner scanner = new Scanner(System.in);
			System.out.println("Give max iterations, max flips and probability:");
			int maxIter = Integer.parseInt(scanner.nextLine());
			int maxFlips = Integer.parseInt(scanner.nextLine());
			double probability = Double.parseDouble(scanner.nextLine());
			SATAlgorithm algorithm = new Algorithm5(formula, maxIter, maxFlips, probability);
			algorithm.perform();
		} else if (noOfAlgorithm == 6) {
			SATAlgorithm algorithm = new Algorithm6(formula);
			algorithm.perform();
		}

	}

	private static SATFormula createSATFormula(List<String> contentOfFile) {
		int numberOfVariables = 0;
		Clause[] clauses = new Clause[1];
		int nOfReadClause = 0;
		for (String line : contentOfFile) {
			line = line.trim();
			if (line.startsWith("c")) {
				continue;
			} else if (line.startsWith("%")) {
				break;
			} else if (line.startsWith("p")) {
				String[] tmp = line.split(" +");
				numberOfVariables = Integer.parseInt(tmp[2]);
				clauses = new Clause[Integer.parseInt(tmp[3])];
			} else {
				String[] tmp = line.split("\\s+");
				int[] indexes = new int[3];
				for (int i = 0; i < 3; i++) {
					indexes[i] = Integer.parseInt(tmp[i]);
				}
				clauses[nOfReadClause++] = new Clause(indexes);
			}
		}
		return new SATFormula(numberOfVariables, clauses);
	}
}
