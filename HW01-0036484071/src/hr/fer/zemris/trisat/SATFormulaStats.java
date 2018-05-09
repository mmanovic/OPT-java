package hr.fer.zemris.trisat;

public class SATFormulaStats {
	private SATFormula formula;
	private boolean isSatisfied;
	private int numberOfSatisfied;
	private double[] post;
	private BitVector assignment;

	private final double percentageConstantUp = 0.01;
	private final double percentageConstantDown = 0.1;
	private final double percentageUnitAmount = 50;

	public SATFormulaStats(SATFormula formula) {
		this.formula = formula;
		post = new double[formula.getNumberOfClauses()];
	}

	// analizira se predano rješenje i pamte svi relevantni pokazatelji
	public void setAssignment(BitVector assignment, boolean updatePercentages) {
		isSatisfied = formula.isSatisfied(assignment);
		int length = formula.getNumberOfClauses();
		numberOfSatisfied = 0;
		for (int i = 0; i < length; i++) {
			if (formula.getClause(i).isSatisfied(assignment)) {
				numberOfSatisfied++;
			}
		}
		this.assignment = assignment;
		if (updatePercentages) {
			for (int i = 0; i < length; i++) {
				if (formula.getClause(i).isSatisfied(assignment)) {
					post[i] += (1 - post[i]) * percentageConstantUp;
				} else {
					post[i] += (0 - post[i]) * percentageConstantDown;
				}
			}
		}

	}

	// vraća temeljem onoga što je setAssignment zapamtio: broj klauzula koje su
	// zadovoljene
	public int getNumberOfSatisfied() {
		return numberOfSatisfied;
	}

	// vraća temeljem onoga što je setAssignment zapamtio
	public boolean isSatisfied() {
		return isSatisfied;
	}

	// vraća temeljem onoga što je setAssignment zapamtio: suma korekcija
	// klauzula
	public double getPercentageBonus() {
		double bonus = 0;
		int numberOfClauses = formula.getNumberOfClauses();
		for (int i = 0; i < numberOfClauses; i++) {
			if (formula.getClause(i).isSatisfied(assignment)) {
				bonus += (1 - post[i]) * percentageUnitAmount;
			} else {
				bonus -= (1 - post[i]) * percentageUnitAmount;
			}
		}
		return bonus;
	}

	// vraća temeljem onoga što je setAssignment zapamtio: procjena postotka za
	// klauzulu
	public double getPercentage(int index) {
		if (index >= 0 && index < post.length) {
			return post[index];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}
}
