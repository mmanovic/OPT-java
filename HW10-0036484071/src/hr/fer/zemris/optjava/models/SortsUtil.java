package hr.fer.zemris.optjava.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import hr.fer.zemris.optjava.operators.Chromosome;

public class SortsUtil {

	public static List<List<Chromosome>> crowdingSort(List<List<Chromosome>> fronts) {
		int frontCounter = 0;
		int objectiveSize = fronts.get(0).get(0).getObjectivesSize();
		double[] mins = new double[objectiveSize];
		double[] maxs = new double[objectiveSize];
		Arrays.fill(mins, Double.MAX_VALUE);
		Arrays.fill(maxs, -Double.MAX_VALUE);
		for (List<Chromosome> front : fronts) {
			for (Chromosome unit : front) {
				unit.setRank(frontCounter);
				unit.setDistance(0);
				double[] objectives = unit.getObjectives();
				for (int i = 0; i < objectiveSize; i++) {
					maxs[i] = Double.max(maxs[i], objectives[i]);
					mins[i] = Double.min(mins[i], objectives[i]);
				}
			}
			frontCounter++;
		}

		for (List<Chromosome> front : fronts) {
			for (int i = 0; i < objectiveSize; i++) {
				int index = i;
				front.sort(Comparator.comparingDouble(x -> x.getObjectives()[index]));
				int frontSize = front.size();
				front.get(0).setDistance(Double.MAX_VALUE);
				front.get(frontSize - 1).setDistance(Double.MAX_VALUE);
				for (int j = 1; j < frontSize - 1; j++) {
					double d = front.get(j).getDistance();
					d += (front.get(j + 1).getObjectives()[i] - front.get(j - 1).getObjectives()[i])
							/ (maxs[i] - mins[i]);
					front.get(j).setDistance(d);
				}
			}
			front.sort(Comparator.comparingDouble(x -> ((Chromosome) x).getDistance()).reversed());
		}

		return fronts;
	}

	public static List<List<Chromosome>> dominationSort(List<Chromosome> population) {
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
