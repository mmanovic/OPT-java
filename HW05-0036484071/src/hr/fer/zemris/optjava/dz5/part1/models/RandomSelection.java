package hr.fer.zemris.optjava.dz5.part1.models;

import java.util.List;
import java.util.Random;

public class RandomSelection implements ISelection {
	private Random random = new Random();

	@Override
	public Chromosome select(List<Chromosome> population) {
		return population.get(random.nextInt(population.size()));
	}

}
