package hr.fer.zemris.generic.ga.operators;

import hr.fer.zemris.generic.ga.GASolution;

public interface IMutation<T> {
	public void mutate(GASolution<T> solution);
}
