package hr.fer.zemris.trisat.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

public class Algorithm4 implements SATAlgorithm {
	private SATFormula formula;
	private SATFormulaStats stats;
	private int maxIter;
	private int maxFlips;

	public Algorithm4(SATFormula formula, int maxIter, int maxFlips) {
		super();
		this.formula = formula;
		this.maxIter = maxIter;
		this.maxFlips = maxFlips;
		this.stats = new SATFormulaStats(formula);
	}

	@Override
	public void perform() {
		int iteration = 0;
		Random random = new Random();

		while (iteration < maxIter) {
			BitVector vector = new BitVector(random, formula.getNumberOfVariables());
			for (int i = 0; i < maxFlips; i++) {
				stats.setAssignment(vector, false);
				if (stats.getNumberOfSatisfied() == formula.getNumberOfClauses()) {
					System.out.println(vector);
					System.out.println("Number of iterations:" + iteration);
					return;
				}
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
				vector = maxSubset.get(random.nextInt(maxSubset.size()));
			}
			iteration++;
		}
		System.out.println("Excedeed number of iterations.");

	}

}
