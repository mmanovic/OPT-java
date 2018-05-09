package hr.fer.zemris.optjava.dz4.part2.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Mutation {
	private Random random = new Random();
	private double mutationProbability;

	public Mutation(double mutationProbability) {
		super();
		this.mutationProbability = mutationProbability;
	}

	public void mutate(Chromosome x) {
		List<Bin> bins = x.getBins();
		List<Stick> removedSticks = new ArrayList<>();
		Iterator<Bin> iterator = bins.iterator();
		while (iterator.hasNext()) {
			if (random.nextDouble() <= mutationProbability) {
				Bin bin = iterator.next();
				removedSticks.addAll(bin.getSticks());
				iterator.remove();
			} else {
				iterator.next();
			}

		}

		removedSticks.sort(Comparator.reverseOrder());
		x.addSticks(removedSticks);
	}
}
