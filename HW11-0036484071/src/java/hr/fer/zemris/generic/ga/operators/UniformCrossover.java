package hr.fer.zemris.generic.ga.operators;

import hr.fer.zemris.generic.ga.Chromosome;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class UniformCrossover implements ICrossover<int[]> {

	@Override
	public GASolution<int[]> crossover(GASolution<int[]> x, GASolution<int[]> y) {
		IRNG random = RNG.getRNG();
		GASolution<int[]> child = new Chromosome();
		int[] xData = x.getData();
		int[] childData = new int[xData.length];
		int[] yData = y.getData();

		childData[0] = xData[0];
		for (int i = 1; i < xData.length; i = i + 5) {
			if (random.nextDouble() <= 0.5) {
				childData[i] = xData[i];
				childData[i + 1] = xData[i + 1];
				childData[i + 2] = xData[i + 2];
				childData[i + 3] = xData[i + 3];
				childData[i + 4] = xData[i + 4];
			} else {
				childData[i] = yData[i];
				childData[i + 1] = yData[i + 1];
				childData[i + 2] = yData[i + 2];
				childData[i + 3] = yData[i + 3];
				childData[i + 4] = yData[i + 4];
			}
		}

		child.setData(childData);
		return child;
	}

}
