package hr.fer.zemris.generic.ga.operators;

import hr.fer.zemris.art.GrayScaleImage;
import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

public class SingleMutation implements IMutation<int[]> {
	private double backgroundChangeProb;
	private double sigma;
	private int width;
	private int height;
	private int nOfRectangles;

	public SingleMutation(double backgroundChangeProb, double sigma, int width, int height, int nOfRectangles) {
		this.backgroundChangeProb = backgroundChangeProb;
		this.sigma = sigma;
		this.width = width;
		this.height = height;
		this.nOfRectangles = nOfRectangles;
	}

	@Override
	public void mutate(GASolution<int[]> solution) {
		IRNG random = RNG.getRNG();
		int[] data = solution.getData();
		int index = 1 + random.nextInt(0, nOfRectangles) * 5;

		if (random.nextDouble() < backgroundChangeProb) {
			data[0] = add(data[0], 0, GrayScaleImage.GREY_SHADES);
		}
		data[index] = add(data[index], 0, width - 1);
		data[index + 1] = add(data[index + 1], 0, height - 1);
		data[index + 2] = add(data[index + 2], 1, width - data[index]);
		data[index + 3] = add(data[index + 3], 1, height - data[index + 1]);
		data[index + 4] = add(data[index + 4], 0, GrayScaleImage.GREY_SHADES);
	}

	private int add(int data, int min, int max) {
		IRNG random = RNG.getRNG();
		data += (int) (sigma * random.nextGaussian());
		data = Math.max(data, min);
		data = Math.min(data, max);
		return data;
	}
}
