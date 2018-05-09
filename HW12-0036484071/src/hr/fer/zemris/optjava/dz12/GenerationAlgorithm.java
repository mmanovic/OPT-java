package hr.fer.zemris.optjava.dz12;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz12.GAmodels.Chromosome;
import hr.fer.zemris.optjava.dz12.GAmodels.ICrossover;
import hr.fer.zemris.optjava.dz12.GAmodels.IMutation;
import hr.fer.zemris.optjava.dz12.GAmodels.ISelection;
import hr.fer.zemris.optjava.dz12.GAmodels.OnePointCrossover;
import hr.fer.zemris.optjava.dz12.GAmodels.OnePointMutation;
import hr.fer.zemris.optjava.dz12.GAmodels.TournamentSelection;
import hr.fer.zemris.optjava.dz12.models.AntWorld;

public class GenerationAlgorithm {
	private static int maxInitDepth = 6;
	private static int maxDepth = 10;
	private static int maxNodes = 200;
	private static int maxEffort = 10;// koliko puta cemo pokusati krizati
										// roditelje prije nego sto vratimo
										// kopiju jednog
	private static double plagiarism = 0.9;
	private static int selectionSize = 7;
	private static double reproductionProb = 0.1;
	private static double mutationProb = 0.14;
	private static double crossoverProb = 0.85;
	private static boolean ELITISM = true;
	private static int elitismSize = 1;
	private int populationSize;
	private int maxGeneration;
	private double minFitness;
	private Random random = new Random();
	private AntWorld world;

	public GenerationAlgorithm(int populationSize, int maxGeneration, double minFitness, AntWorld world) {
		super();
		this.world = world;
		this.populationSize = populationSize;
		this.maxGeneration = maxGeneration;
		this.minFitness = minFitness;
	}

	public Chromosome run() {
		IMutation mutation = new OnePointMutation(maxDepth, maxNodes);
		ICrossover crossover = new OnePointCrossover(maxDepth, maxNodes, maxEffort);
		ISelection selection = new TournamentSelection(selectionSize);
		PopulationCreator creator = new PopulationCreator(populationSize, maxInitDepth, maxNodes);
		List<Chromosome> population = creator.createPopulation();
		for (Chromosome unit : population) {
			unit.evaluate(world.copy(), false);
		}

		for (int generation = 0; generation < maxGeneration; generation++) {
			population.sort(Comparator.reverseOrder());
			Chromosome best = population.get(0);
			if (best.getFitness() >= minFitness) {
				System.out.println("Final solution fitness: " + best.getFitness());
				return best;
			}
			System.out.println("Generation: " + generation + " Fitness: " + best.getFitness());
			List<Chromosome> newPopulation = new ArrayList<>();
			if (ELITISM) {
				for (int i = 0; i < elitismSize; i++) {
					newPopulation.add(population.get(i).copy());
				}
			}

			while (newPopulation.size() < populationSize) {
				double operator = random.nextDouble();
				if (operator <= reproductionProb) {
					Chromosome child = selection.select(population).copy();
					child.setFitness(child.getFitness() * plagiarism);
					newPopulation.add(child);
				} else if (operator <= reproductionProb + mutationProb) {
					Chromosome child = mutation.mutate(selection.select(population));
					child.evaluate(world.copy(), false);
					newPopulation.add(child);
				} else {
					Chromosome x = selection.select(population);
					Chromosome y = selection.select(population);
					Chromosome child = crossover.cross(x, y);
					child.evaluate(world.copy(), false);
					if (child.getFitness() == x.getFitness() || child.getFitness() == y.getFitness()) {
						child.setFitness(child.getFitness() * plagiarism);
					}
					newPopulation.add(child);
				}
			}

			population = newPopulation;
		}
		population.sort(Comparator.reverseOrder());
		Chromosome best = population.get(0);
		System.out.println("Final solution fitness: " + best.getFitness());
		return best;

	}

}
