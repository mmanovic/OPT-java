package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm2 implements SATAlgorithm {
	private SATFormula formula;
	private SATFormulaStats stats;
	private static int MAX_ITERATIONS = 100000;

	public Algorithm2(SATFormula formula) {
		super();
		this.formula = formula;
		this.stats = new SATFormulaStats(formula);
	}

	@Override
	public void perform() {
		Random random = new Random();
		BitVector vector = new BitVector(random, formula.getNumberOfVariables());
		int iteration = 0;
		stats.setAssignment(vector, false);
		int currFitness = stats.getNumberOfSatisfied();
		if (currFitness == formula.getNumberOfClauses()) {
			System.out.println(vector);
			return;
		}
		while (iteration < MAX_ITERATIONS) {
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
				System.out.println("Unsuccess at searching.Local optimum.");
				System.out.println(iteration);
				return;
			} else {
				vector = maxSubset.get(random.nextInt(maxSubset.size()));
				currFitness = maxFitness;
				if (currFitness == formula.getNumberOfClauses()) {
					System.out.println(maxSubset);
					System.out.println("Number of iterations:" + iteration);
					return;
				}
			}
			iteration++;
		}
		System.out.println("Excedeed number of iterations.");
	}

}
