package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.MutableBitVector;
import hr.fer.zemris.trisat.SATFormula;

public class Algorithm1 implements SATAlgorithm {
	private SATFormula formula;

	public Algorithm1(SATFormula formula) {
		super();
		this.formula = formula;
	}

	@Override
	public void perform() {
		MutableBitVector vector = new MutableBitVector(formula.getNumberOfVariables());
		int size = (int) Math.pow(2, formula.getNumberOfVariables());
		for (int i = 0; i < size; i++) {
			if (formula.isSatisfied(vector)) {
				System.out.println(vector);
			}
			vector.increment();
		}

	}

}
