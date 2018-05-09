package hr.fer.zemris.generic.ga.operators;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class UniformMutation implements IMutation<int[]> {
	private double rectangleMutationRate;
	private int width;
	private int height;

	public UniformMutation(double rectangleMutationRate, int width, int height) {
		super();
		this.rectangleMutationRate = rectangleMutationRate;
		this.width = width;
		this.height = height;
	}

	@Override
	public void mutate(GASolution<int[]> solution) {
		IRNG random = RNG.getRNG();
		int[] data = solution.getData();

		int limit = data.length;
		for (int i = 1; i < limit; i = i + 5) {
			if (random.nextDouble() >= rectangleMutationRate) {
				continue;
			}
			data[i] = random.nextInt(0, width - 1);
			data[i + 1] = random.nextInt(0, height - 1);
			data[i + 2] = random.nextInt(1, 10);
			data[i + 3] = random.nextInt(1, 10);
			data[i + 4] = random.nextInt(0, GrayScaleImage.GREY_SHADES);
		}
	}

}
