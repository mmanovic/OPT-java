package hr.fer.zemris.optjava.dz5.part1.models;

import java.util.List;

public interface ISelection {
	public Chromosome select(List<Chromosome> population);
}
