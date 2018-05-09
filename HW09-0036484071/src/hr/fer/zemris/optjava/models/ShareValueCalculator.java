package hr.fer.zemris.optjava.models;

import hr.fer.zemris.optjava.operators.Chromosome;

public class ShareValueCalculator {
	private double alpha;
	private double sigma;
	private SpaceType type;

	public ShareValueCalculator(double alpha, double sigma, SpaceType type) {
		super();
		this.alpha = alpha;
		this.sigma = sigma;
		this.type = type;
	}

	public double getShareValue(Chromosome x, Chromosome y) {
		double distance = -1;
		if (type == SpaceType.DECISION_SPACE) {
			distance = solutionDistance(x, y);
		} else {
			distance = objectiveDistance(x, y);
		}
		if (distance < sigma) {
			return 1.0 - Math.pow(distance / sigma, alpha);
		} else {
			return 0;
		}
	}

	private double solutionDistance(Chromosome x, Chromosome y) {
		double[] xValues = x.getValues();
		double[] yValues = y.getValues();
		double distance = 0;
		for (int i = 0; i < xValues.length; i++) {
			distance += Math.pow((xValues[i] - yValues[i]) / (Chromosome.maxs[i] - Chromosome.mins[i]), 2);
		}
		return distance;
	}

	private double objectiveDistance(Chromosome x, Chromosome y) {
		double[] xValues = x.getObjectives();
		double[] yValues = y.getObjectives();
		double distance = 0;
		for (int i = 0; i < xValues.length; i++) {
			distance += Math.pow((xValues[i] - yValues[i]), 2);
		}
		return distance;
	}

}
