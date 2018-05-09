package hr.fer.zemris.optjava.operators;

import java.util.List;

public interface ISelection {
	public Chromosome select(List<Chromosome> population);
}
