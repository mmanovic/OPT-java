package hr.fer.zemris.optjava.operators;

import java.util.List;
import java.util.Random;

public class CrowdedSelection implements ISelection {
	private Random random = new Random();

	@Override
	public Chromosome select(List<Chromosome> population) {
		Chromosome x = population.get(random.nextInt(population.size()));
		Chromosome y = population.get(random.nextInt(population.size()));
		if (x.getRank() < y.getRank()) {
			return x;
		} else if (x.getRank() > y.getRank()) {
			return y;
		} else {
			if (x.getDistance() > y.getDistance()) {
				return x;
			} else {
				return y;
			}
		}
	}

}
