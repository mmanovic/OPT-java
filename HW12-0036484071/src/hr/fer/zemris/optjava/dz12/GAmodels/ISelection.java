package hr.fer.zemris.optjava.dz12.GAmodels;

import java.util.List;

public interface ISelection {
	public Chromosome select(List<Chromosome> population);
}
