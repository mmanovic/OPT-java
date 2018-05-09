package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm3 implements SATAlgorithm {
	private SATFormula formula;
	private SATFormulaStats stats;
	private static int MAX_ITERATIONS = 10000;
	private final int numberOfBest = 2;

	public Algorithm3(SATFormula formula) {
		this.formula = formula;
		this.stats = new SATFormulaStats(formula);
	}

	@Override
	public void perform() {
		Random random = new Random();
		BitVector vector = new BitVector(random, formula.getNumberOfVariables());
		int iteration = 0;
		while (iteration < MAX_ITERATIONS) {
			stats.setAssignment(vector, true);
			int currFitness = stats.getNumberOfSatisfied();
			if (currFitness == formula.getNumberOfClauses()) {
				System.out.println(vector);
				System.out.println("Iterations: " + iteration);
				return;
			}
			BitVectorNGenerator generator = new BitVectorNGenerator(vector);
			MutableBitVector[] neighborhoods = generator.createNeighborhood();
			List<BitVector> newSolutions = new ArrayList<>();
			for (MutableBitVector neighborhood : neighborhoods) {
				newSolutions.add(neighborhood.copy());
			}
			Collections.sort(newSolutions, new Comparator<BitVector>() {

				@Override
				public int compare(BitVector v1, BitVector v2) {
					stats.setAssignment(v1, false);
					double fitness1 = stats.getNumberOfSatisfied() + stats.getPercentageBonus();
					stats.setAssignment(v2, false);
					double fitness2 = stats.getNumberOfSatisfied() + stats.getPercentageBonus();
					return Double.compare(fitness2, fitness1);
				}
			});

			vector = newSolutions.get(random.nextInt(numberOfBest));
			iteration++;
		}
		System.out.println("Excedeed number of iterations.");
	}

}
