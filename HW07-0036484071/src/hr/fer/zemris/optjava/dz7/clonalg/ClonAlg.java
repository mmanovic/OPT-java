package hr.fer.zemris.optjava.dz7.clonalg;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import hr.fer.zemris.optjava.dz7.models.FFANN;
import hr.fer.zemris.optjava.dz7.models.FFANNFunction;
import hr.fer.zemris.optjava.dz7.models.IFunction;

public class ClonAlg {
	private double merr;
	private int maxIteration;
	private IFunction function;
	private int populationSize;
	private int dimension;

	private int N_NEWBORN;
	public static double BETA = 2;
	public static double RO = 4;

	private List<AntiBody> population;

	public ClonAlg(double merr, int maxIteration, int populationSize, FFANN ffann) {
		super();
		this.merr = merr;
		this.maxIteration = maxIteration;
		this.populationSize = populationSize;
		this.dimension = ffann.getWeightsCount();
		this.function = new FFANNFunction(ffann);
		N_NEWBORN = (int) (0.1 * populationSize);
		population = createPopulation();
	}

	public double[] run() {
		AntiBody best = null;
		double bestError = Double.MAX_VALUE;
		int iteration = 0;
		while (iteration < maxIteration && bestError > merr) {
			population.sort(Comparator.naturalOrder());
			if (population.get(0).getError() < bestError) {
				bestError = population.get(0).getError();
				best = population.get(0);
			}
			if (iteration % 10 == 0) {
				System.out.println("Iteration " + iteration + " Current error: " + bestError);
			}
			List<AntiBody> clones = new ArrayList<>();
			for (int i = 0; i < populationSize; i++) {
				int numberOfClones = (int) (BETA * populationSize / (i + 1));
				AntiBody antibody = population.get(i);
				for (int j = 0; j < numberOfClones; j++) {
					clones.add(antibody.clone());
				}
			}
			hyperMutate(clones);
			population.addAll(clones);
			population.sort(Comparator.naturalOrder());
			population = population.subList(0, populationSize - N_NEWBORN);
			for (int i = 0; i < N_NEWBORN; i++) {
				population.add(new AntiBody(function, dimension));
			}
			iteration++;
		}
		double[] bestWeights = best.getValues();
		System.out.println("Final solution: ");
		for (int i = 0; i < dimension; i++) {
			System.out.print(bestWeights[i] + " ");
		}
		System.out.println("] ");
		System.out.println("Error: " + bestError);
		return bestWeights;
	}

	private List<AntiBody> createPopulation() {
		List<AntiBody> population = new ArrayList<>();
		for (int i = 0; i < populationSize; i++) {
			population.add(new AntiBody(function, dimension));
		}
		return population;
	}

	private void hyperMutate(List<AntiBody> clones) {
		for (AntiBody a : clones) {
			a.mutate(Math.exp(-RO * clones.get(0).getError()));
		}
	}

}
