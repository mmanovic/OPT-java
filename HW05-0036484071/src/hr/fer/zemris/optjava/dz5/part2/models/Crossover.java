package hr.fer.zemris.optjava.dz5.part2.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Vrsta križanja koja je ovdje implementirana je križanje poretkom OX1.
 * 
 * @author Mato
 *
 */
public class Crossover {
	private Random random = new Random();

	public Chromosome cross(Chromosome x, Chromosome y) {
		int[] distribution1 = x.getDistribution();
		int[] distribution2 = y.getDistribution();
		int n = distribution1.length;
		int rand1 = random.nextInt(n);
		int rand2 = random.nextInt(n);
		int crossPoint1 = Integer.min(rand1, rand2);
		int crossPoint2 = Integer.max(rand1, rand2);

		int[] distributionChild = new int[n];
		Set<Integer> duplicate = new HashSet<>();
		// kopiranje dijela prvog roditelja u dijete
		for (int i = crossPoint1; i <= crossPoint2; i++) {
			distributionChild[i] = distribution1[i];
			duplicate.add(distribution1[i]);
		}

		List<Integer> orderOfAddition = new ArrayList<>();
		// uzimamo od drugog roditelja gene koje æemo dodati dijetetu u toèno
		// odreðenom poretku
		for (int i = crossPoint2 + 1; i < n; i++) {
			if (!duplicate.contains(distribution2[i])) {
				orderOfAddition.add(distribution2[i]);
			}
		}
		for (int i = 0; i <= crossPoint2; i++) {
			if (!duplicate.contains(distribution2[i])) {
				orderOfAddition.add(distribution2[i]);
			}
		}

		// dodajemo u dijete gene drugog roditelja
		for (int i = crossPoint2 + 1; i < n; i++) {
			distributionChild[i] = orderOfAddition.remove(0);
		}
		for (int i = 0; i < crossPoint1; i++) {
			distributionChild[i] = orderOfAddition.remove(0);
		}

		return new Chromosome(x.getFunction(), distributionChild);
	}
}
