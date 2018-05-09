package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm6 implements SATAlgorithm {
	private SATFormula formula;
	private SATFormulaStats stats;
	private static int MAX_ITERATION = 5;
	private static int MAX_ITERATION_OF_LOCAL_SEARCH = 10000;
	private static double PERCENTAGE = 0.05;

	public Algorithm6(SATFormula formula) {
		super();
		this.formula = formula;
		this.stats = new SATFormulaStats(formula);
	}

	@Override
	public void perform() {
		Random random = new Random();
		BitVector vector = new BitVector(random, formula.getNumberOfVariables());
		int iteration = 0;
		vector = localSearch(vector);
		BitVector vector2;
		while (iteration < MAX_ITERATION) {
			stats.setAssignment(vector, false);
			int currFitness = stats.getNumberOfSatisfied();
			if (currFitness == formula.getNumberOfClauses()) {
				System.out.println(vector);
				return;
			}
			vector = perturbing(vector);
			vector2 = localSearch(vector);
			stats.setAssignment(vector, false);
			int fit1 = stats.getNumberOfSatisfied();
			stats.setAssignment(vector2, false);
			int fit2 = stats.getNumberOfSatisfied();
			if (fit1 < fit2) {
				vector = vector2;
			}
			iteration++;
		}
		stats.setAssignment(vector, false);
		int currFitness = stats.getNumberOfSatisfied();
		System.out.println("Excedeed number of iterations.");
		System.out.println("The best solution found is " + vector + ".");
		System.out.println(currFitness + "/" + formula.getNumberOfClauses());

	}

	private BitVector perturbing(BitVector vector) {
		int size = vector.getSize();
		int nOfPerturbation = (int) (size * PERCENTAGE);
		MutableBitVector copy = vector.copy();
		Random random = new Random();
		for (int i = 0; i < nOfPerturbation; i++) {
			int index = random.nextInt(size);
			copy.set(index, !copy.get(index));
		}
		return copy;
	}

	private BitVector localSearch(BitVector vector) {
		Random random = new Random();
		int iteration = 0;
		stats.setAssignment(vector, false);
		int currFitness = stats.getNumberOfSatisfied();

		while (iteration < MAX_ITERATION_OF_LOCAL_SEARCH) {
			BitVectorNGenerator generator = new BitVectorNGenerator(vector);
			MutableBitVector[] neighborhoods = generator.createNeighborhood();
			int maxFitness = -1;
			List<MutableBitVector> maxSubset = new ArrayList();
			for (MutableBitVector neighborhood : neighborhoods) {
				stats.setAssignment(neighborhood, false);
				if (stats.getNumberOfSatisfied() > maxFitness) {
					maxFitness = stats.getNumberOfSatisfied();
					maxSubset = new ArrayList<>();
					maxSubset.add(neighborhood);
				} else if (stats.getNumberOfSatisfied() == maxFitness) {
					maxSubset.add(neighborhood);
				}
			}
			if (maxFitness < currFitness) {
				return vector;
			} else {
				vector = maxSubset.get(random.nextInt(maxSubset.size()));
				currFitness = maxFitness;
				if (currFitness == formula.getNumberOfClauses()) {
					return vector;
				}
			}
			iteration++;
		}
		return vector;
	}

}
