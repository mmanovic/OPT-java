package hr.fer.zemris.generic.ga.operators;

import hr.fer.zemris.generic.ga.GASolution;

public interface ICrossover<T> {
	public GASolution<T> crossover(GASolution<T> x, GASolution<T> y);
}
