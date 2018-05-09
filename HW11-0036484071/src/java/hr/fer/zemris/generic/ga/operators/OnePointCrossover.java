package hr.fer.zemris.generic.ga.operators;

import hr.fer.zemris.generic.ga.Chromosome;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class OnePointCrossover implements ICrossover<int[]> {
	private int nOfRectangles;

	public OnePointCrossover(int nOfRectangles) {
		super();
		this.nOfRectangles = nOfRectangles;
	}

	@Override
	public GASolution<int[]> crossover(GASolution<int[]> x, GASolution<int[]> y) {
		IRNG random = RNG.getRNG();
		GASolution<int[]> child = new Chromosome();
		int[] xData = x.getData();
		int[] childData = new int[xData.length];
		int[] yData = y.getData();
		int crossPoint = random.nextInt(0, nOfRectangles);

		for (int i = 0; i <= crossPoint * 5; i++) {
			childData[i] = xData[i];
		}
		for (int i = crossPoint * 5 + 1; i < childData.length; i++) {
			childData[i] = yData[i];
		}
		child.setData(childData);
		return child;
	}

}
