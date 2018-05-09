package hr.fer.zemris.trisat;

public class Clause {
	private int[] indexes;

	public Clause(int[] indexes) {
		this.indexes = indexes;
	}

	// vraća broj literala koji čine klauzulu
	public int getSize() {
		return indexes.length;
	}

	// vraća indeks varijable koja je index-ti član ove klauzule
	public int getLiteral(int index) {
		if (index >= 0 && index < getSize()) {
			return indexes[index];
		} else {
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	// vraća true ako predana dodjela zadovoljava ovu klauzulu
	public boolean isSatisfied(BitVector assignment) {
		for (int index : indexes) {
			if (index < 0) {
				if (assignment.get(-(index + 1)) == false) {
					return true;
				}
			} else {
				if (assignment.get(index - 1) == true) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (int index : indexes) {
			if (index < 0) {
				string.append("~x");
				string.append(-index + " + ");
			} else {
				string.append("x" + index + " + ");
			}
		}
		return string.substring(0, string.length() - 3);
	}
}
