package hr.fer.zemris.generic.ga.operators;

import hr.fer.zemris.generic.ga.GASolution;

import java.util.List;

public interface ISelection<T> {
	public GASolution<T> select(List<GASolution<T>> population);
}
