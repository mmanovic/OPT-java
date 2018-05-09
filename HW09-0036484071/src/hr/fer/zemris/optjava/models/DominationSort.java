package hr.fer.zemris.optjava.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hr.fer.zemris.optjava.operators.Chromosome;

public class DominationSort {
	

	public static List<List<Chromosome>> getFronts(List<Chromosome> population) {
		List<List<Chromosome>> fronts = new ArrayList<>();
		int populationSize = population.size();
		HashMap<Integer, List<Integer>> dominateOver = new HashMap<>();
		HashMap<Integer, Integer> numberDominatedBy = new HashMap<>();
		for (int i = 0; i < populationSize; i++) {
			dominateOver.put(i, new ArrayList<>());
			numberDominatedBy.put(i, 0);
		}

		for (int i = 0; i < populationSize; i++) {
			Chromosome x = population.get(i);
			for (int j = 0; j < populationSize; j++) {
				if (i == j) {
					continue;
				} else if (x.dominateOver(population.get(j))) {
					dominateOver.get(i).add(j);
					int tmp = numberDominatedBy.get(j) + 1;
					numberDominatedBy.put(j, tmp);
				}
			}
		}

		while (dominateOver.size() > 0) {
			List<Chromosome> front = new ArrayList<>();
			List<Integer> frontIndices = new ArrayList<>();
			for (int i = 0; i < populationSize; i++) {
				if (dominateOver.containsKey(i) && numberDominatedBy.get(i) == 0) {
					frontIndices.add(i);
				}
			}
			for (Integer index : frontIndices) {
				front.add(population.get(index));
				List<Integer> dominateIndices = dominateOver.remove(index);
				for (Integer pointer : dominateIndices) {
					int tmp = numberDominatedBy.get(pointer) - 1;
					numberDominatedBy.put(pointer, tmp);
				}
			}

			fronts.add(front);
		}
		return fronts;
	}

	

}
