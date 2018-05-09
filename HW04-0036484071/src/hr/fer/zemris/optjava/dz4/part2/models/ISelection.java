package hr.fer.zemris.optjava.dz4.part2.models;

import java.util.List;

public interface ISelection {
	public Chromosome select(List<Chromosome> population);
}
