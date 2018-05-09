package hr.fer.zemris.optjava.dz4.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.optjava.dz4.part1.models.Chromosome;
import hr.fer.zemris.optjava.dz4.part1.models.Crossover;
import hr.fer.zemris.optjava.dz4.part1.models.Function;
import hr.fer.zemris.optjava.dz4.part1.models.ISelection;
import hr.fer.zemris.optjava.dz4.part1.models.Mutation;
import hr.fer.zemris.optjava.dz4.part1.models.RouletteWheelSelection;
import hr.fer.zemris.optjava.dz4.part1.models.TournamentSelection;

/**
 * @author Mato Preporuèeni parametri 150 0.05 2500 tournament:8 0.01.
 */
public class GeneticAlgorithm {
	public static int elitism = 2;// koliko najboljih jedinki prenosimo u iducu
									// generaciju
	public static Path file = Paths.get("./zad-prijenosna.txt");
	public static double alpha = 0.4;

	public static void main(String[] args) {
		int populationSize = Integer.parseInt(args[0]);
		double epsilon = Double.parseDouble(args[1]);
		int maxGeneration = Integer.parseInt(args[2]);
		double sigma = Double.parseDouble(args[4]);
		ISelection selection;
		if (args[3].equalsIgnoreCase("rouletteWheel")) {
			selection = new RouletteWheelSelection();
		} else if (args[3].startsWith("tournament:")) {
			String[] tmp = args[3].split(":");
			selection = new TournamentSelection(Integer.parseInt(tmp[1]));
		} else {
			return;
		}
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
		Function function = getFunction(lines);
		Mutation mutation = new Mutation(sigma);
		Crossover crossover = new Crossover(alpha);
		List<Chromosome> population = createPopulation(function, populationSize);
		population.sort(Comparator.reverseOrder());

		int generation = 0;
		double error = Double.MAX_VALUE;
		while (error > epsilon && generation < maxGeneration) {
			List<Chromosome> newPopulation = new ArrayList<>();
			for (int j = 0; j < elitism; j++) {
				newPopulation.add(population.get(j));
			}
			while (newPopulation.size() < populationSize) {
				Chromosome x = selection.select(population);
				Chromosome y = selection.select(population);
				Chromosome child = mutation.mutate(crossover.cross(x, y));
				newPopulation.add(child);
			}
			newPopulation.sort(Comparator.reverseOrder());
			population = newPopulation;
			error = -population.get(0).getFitness();
			generation++;
			System.out.println(error);
		}

		System.out.println("Final solution: " + population.get(0));
		System.out.println("Error value: " + error);

	}

	private static List<Chromosome> createPopulation(Function function, int populationSize) {
		int CHROMOSOME_SIZE = 6;
		List<Chromosome> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			population.add(new Chromosome(function, CHROMOSOME_SIZE));
		}
		return population;
	}

	private static Function getFunction(List<String> lines) {
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
