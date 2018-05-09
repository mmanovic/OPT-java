package hr.fer.zemris.optjava.dz5.part2.models;

import java.util.Random;

import hr.fer.zemris.optjava.dz5.part2.models.Chromosome;

/**
 * Obièna mutacija zamjenom elemenata na sluèajno odabrana dva indeksa.
 * 
 * @author Mato
 *
 */
public class Mutation {
	private Random random = new Random();

	public void mutate(Chromosome x) {
		int[] distribution = x.getDistribution();
		int n = distribution.length;
		int index1 = random.nextInt(n);
		int index2 = random.nextInt(n);
		int tmp = distribution[index1];
		distribution[index1] = distribution[index2];
		distribution[index2] = tmp;
		x.setDistribution(distribution);
	}
}
