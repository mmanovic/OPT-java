package hr.fer.zemris.generic.ga;

import java.util.Random;

import hr.fer.zemris.art.GrayScaleImage;

public class Chromosome extends GASolution<int[]> {
	public int nOfRectangles;
	private int maxRange = 10;// maksimalna pocetna sirina ili visina
								// pravokutnika

	public Chromosome(int nOfRectangles, int height, int width) {
		this.nOfRectangles = nOfRectangles;
		data = new int[1 + 5 * nOfRectangles];
		Random random = new Random();
		data[0] = random.nextInt(GrayScaleImage.GREY_SHADES);
		int index = 1;
		for (int i = 0; i < nOfRectangles; i++) {
			data[index] = random.nextInt(width);
			data[index + 1] = random.nextInt(height);
			data[index + 2] = random.nextInt(maxRange) + 1;
			data[index + 3] = random.nextInt(maxRange) + 1;
			data[index + 4] = random.nextInt(GrayScaleImage.GREY_SHADES);
			index += 5;
		}
	}

	public Chromosome() {

	}

	@Override
	public GASolution<int[]> duplicate() {
		Chromosome duplicate = new Chromosome();
		int[] copy = new int[this.data.length];
		System.arraycopy(this.data, 0, copy, 0, this.data.length);
		duplicate.setData(copy);
		duplicate.fitness = this.fitness;
		duplicate.nOfRectangles = nOfRectangles;
		return duplicate;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			sb.append(data[i] + "\n");
		}
		return sb.toString();
	}

	@Override
	public int getNOfRectangles() {
		return nOfRectangles;
	}

}
