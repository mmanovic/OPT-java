package hr.fer.zemris.trisat;

public class SATFormula {
	private Clause[] clauses;
	private int numberOfVariables;

	public SATFormula(int numberOfVariables, Clause[] clauses) {
		this.clauses = clauses;
		this.numberOfVariables = numberOfVariables;
	}

	public int getNumberOfVariables() {
		return numberOfVariables;
	}

	public int getNumberOfClauses() {
		return clauses.length;
	}

	public Clause getClause(int index) {
		if (index >= 0 && index < getNumberOfClauses()) {
			return clauses[index];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public boolean isSatisfied(BitVector assignment) {
		for (Clause clause : clauses) {
			if (!clause.isSatisfied(assignment)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Clause clause : clauses) {
			string.append("(" + clause.toString() + ")");
		}
		return string.toString();
	}

}
