package hr.fer.zemris.optjava.dz8.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz8.neural.AbstractANN;

public class DifferentialEvolution {
	private AbstractANN ann;
	private double merr;
	private int populationSize;
	private int maxIteration;
	private double F;
	private double Cr;

	private List<DoubleArraySolution> population;
	private DoubleArraySolution best;
	private Random random = new Random();

	public DifferentialEvolution(AbstractANN ann, double merr, int populationSize, int maxIteration, double f,
			double cr) {
		super();
		this.ann = ann;
		this.merr = merr;
		this.populationSize = populationSize;
		this.maxIteration = maxIteration;
		F = f;
		Cr = cr;
		int dimension = ann.getWeightsCount();
		best = new DoubleArraySolution(dimension);
		population = new ArrayList<>(populationSize);
		best.setError(ann.calcError(best.values));
		population.add(best);
		for (int i = 1; i < populationSize; i++) {
			DoubleArraySolution solution = new DoubleArraySolution(dimension);
			solution.setError(ann.calcError(solution.values));
			if (solution.getError() <= best.getError()) {
				best = solution;
			}
			population.add(solution);
		}
	}

	public void run() {
		int iteration = 0;
		while (iteration < maxIteration && best.getError() > merr) {
			List<DoubleArraySolution> newPopulation = new ArrayList<>();
			for (int i = 0; i < populationSize; i++) {
				DoubleArraySolution target = population.get(i);

				// Ovdje se može odabrati strategija kojom se stvara mutant
				// vektor pa iz njega probni. Najboljom strategijom se nekak
				// pokazuje best strategija,ali i za ostale bi se mogli podesiti
				// parametri F i Cr pa da rade isto dobro.

				// DoubleArraySolution mutant = randomStrategy(i);
				DoubleArraySolution mutant = bestStrategy(i);
				// DoubleArraySolution mutant = targetToBestStrategy(i);

				DoubleArraySolution trialVector = DoubleArraySolution.crossover(target, mutant, Cr);
				trialVector.setError(ann.calcError(trialVector.values));
				if (trialVector.getError() <= target.getError()) {
					newPopulation.add(trialVector);
					if (trialVector.getError() <= best.getError()) {
						best = trialVector;
					}
				} else {
					newPopulation.add(target);
				}
			}
			population = newPopulation;
			iteration++;
			if (iteration % 1 == 0) {
				System.out.println("Iteration: " + iteration + " Error: " + best.getError());
			}
		}
		System.out.println("Final solution error: " + best.getError());
	}

	private DoubleArraySolution targetToBestStrategy(int indexOfTarget) {
		int r1index;
		do {
			r1index = random.nextInt(populationSize);
		} while (r1index == indexOfTarget);
		int r2index;
		do {
			r2index = random.nextInt(populationSize);
		} while (r2index == indexOfTarget || r2index == r1index);
		DoubleArraySolution target = population.get(indexOfTarget);
		DoubleArraySolution mutant = calculateMutant(target, population.get(r1index), population.get(r2index));
		int dimension = target.values.length;
		for (int i = 0; i < dimension; i++) {
			mutant.values[i] += F * (best.values[i] - target.values[i]);
		}
		return mutant;
	}

	private DoubleArraySolution randomStrategy(int indexOfTarget) {
		int r0index;
		do {
			r0index = random.nextInt(populationSize);
		} while (r0index == indexOfTarget);
		int r1index;
		do {
			r1index = random.nextInt(populationSize);
		} while (r1index == indexOfTarget || r1index == r0index);
		int r2index;
		do {
			r2index = random.nextInt(populationSize);
		} while (r2index == indexOfTarget || r2index == r0index || r2index == r1index);
		return calculateMutant(population.get(r0index), population.get(r1index), population.get(r2index));
	}

	private DoubleArraySolution bestStrategy(int indexOfTarget) {
		int r1index;
		do {
			r1index = random.nextInt(populationSize);
		} while (r1index == indexOfTarget);
		int r2index;
		do {
			r2index = random.nextInt(populationSize);
		} while (r2index == indexOfTarget || r2index == r1index);
		return calculateMutant(best, population.get(r1index), population.get(r2index));
	}

	private DoubleArraySolution calculateMutant(DoubleArraySolution r0, DoubleArraySolution r1,
			DoubleArraySolution r2) {
		int dimension = ann.getWeightsCount();
		double[] newValues = new double[dimension];
		for (int i = 0; i < dimension; i++) {
			newValues[i] = r0.values[i] + F * (r1.values[i] - r2.values[i]);
		}
		return new DoubleArraySolution(newValues);
	}

}
