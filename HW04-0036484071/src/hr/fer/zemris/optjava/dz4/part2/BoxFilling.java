package hr.fer.zemris.optjava.dz4.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.optjava.dz4.part2.models.Mutation;
import hr.fer.zemris.optjava.dz4.part2.models.TournamentSelection;
import hr.fer.zemris.optjava.dz4.part2.models.TournamentWorstSelection;
import hr.fer.zemris.optjava.dz4.part2.models.Bin;
import hr.fer.zemris.optjava.dz4.part2.models.Chromosome;
import hr.fer.zemris.optjava.dz4.part2.models.Crossover;
import hr.fer.zemris.optjava.dz4.part2.models.Function;
import hr.fer.zemris.optjava.dz4.part2.models.ISelection;
import hr.fer.zemris.optjava.dz4.part2.models.Stick;

/**
 * Preporuèeni parametri: stazaDoDatoteke 30 3 3 true 30 10 0.3
 * 
 * @author Mato
 *
 */
public class BoxFilling {
	public static int MAX_HEIGHT = 20;
	public static double k = 1.5; // eksponent u funkciji fitnessa
	public static Function function = new Function(k);

	public static void main(String[] args) {
		Path filePath = Paths.get(args[0]);
		int populationSize = Integer.parseInt(args[1]);
		int n = Integer.parseInt(args[2]);
		int m = Integer.parseInt(args[3]);
		boolean p = Boolean.getBoolean(args[4]);
		int maxIteration = Integer.parseInt(args[5]);
		int acceptableSolution = Integer.parseInt(args[6]);
		double mutationProbability = Double.parseDouble(args[7]);

		List<Stick> sticks = getSticks(filePath);
		List<Chromosome> population = getPopulation(populationSize, sticks);
		Crossover crossover = new Crossover();
		Mutation mutation = new Mutation(mutationProbability);
		ISelection selection = new TournamentSelection(n);
		ISelection worstSelection = new TournamentWorstSelection(m);

		Chromosome best = null;
		for (int i = 0; i < maxIteration; i++) {
			Chromosome x = selection.select(population);
			Chromosome y = selection.select(population);
			Chromosome child = crossover.cross(x, y);
			mutation.mutate(child);

			Chromosome worst = worstSelection.select(population);

			if ((p && child.getFitness() >= worst.getFitness()) | !p) {
				population.remove(worst);
				population.add(child);
			}

			population.sort(Comparator.reverseOrder());
			best = population.get(0);
			System.out.println(best.getFitness());
			if (best.getBins().size() <= acceptableSolution) {
				break;
			}
		}
		System.out.println("Final solution: " + best);
		System.out.println("Fitness: " + best.getFitness() + ". Length of the box is: " + best.getBins().size());

	}

	private static List<Chromosome> getPopulation(int populationSize, List<Stick> sticks) {
		// stvaramo onoliko kanti koliko ima štapiæa
		List<Chromosome> chromosomes = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			Collections.shuffle(sticks);
			List<Bin> bins = new ArrayList<>();
			for (Stick stick : sticks) {
				Bin bin = new Bin(MAX_HEIGHT);
				bin.addStick(stick);
				bins.add(bin);
			}
			chromosomes.add(new Chromosome(function, bins));
		}
		return chromosomes;

	}

	private static List<Stick> getSticks(Path filePath) {
		if (!Files.exists(filePath)) {
			System.out.println("File " + filePath.toString() + " doesn't exist.");
			System.exit(0);
		}
		List<String> lines = null;
		try {
			lines = Files.readAllLines(filePath);
		} catch (IOException e) {
			System.out.println("Unable to open file.");
			System.exit(0);
		}
		String[] tmp = lines.get(0).substring(1, lines.get(0).length() - 1).split(", ");
		List<Stick> sticks = new ArrayList<>();
		for (int i = 0; i < tmp.length; i++) {
			sticks.add(new Stick(Integer.parseInt(tmp[i]), i));
		}
		return sticks;
	}

}
