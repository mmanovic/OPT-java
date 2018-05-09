package hr.fer.zemris.optjava.dz7.pso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz7.models.IFunction;

public class Particle {
	public static double C1 = 2;
	public static double C2 = 2;
	public static double X_MIN = -1;
	public static double X_MAX = 1;
	public static double V_MIN = -0.2;
	public static double V_MAX = 0.2;

	private double[] position;
	private double[] speed;

	private double[] bestPosition;
	private double bestPositionError;
	private List<Particle> neighborhoods;

	private int dimension;
	private Random random = new Random();
	private IFunction function;

	public Particle(int dimension, IFunction function) {
		this.dimension = dimension;
		this.function = function;
		position = new double[dimension];
		speed = new double[dimension];
		bestPosition = new double[dimension];
		bestPositionError = Double.MAX_VALUE;
		neighborhoods = new ArrayList<>();
	}

	public void updateSpeed(double inertion) {
		double[] lBest = getBestNeighborhood();
		double[] pBest = bestPosition;

		for (int i = 0; i < position.length; i++) {
			speed[i] += C1 * random.nextDouble() * (pBest[i] - position[i]);
			speed[i] += C2 * random.nextDouble() * (lBest[i] - position[i]);
			speed[i] = Math.min(speed[i], V_MAX);
			speed[i] = Math.max(speed[i], V_MIN);
			position[i] += speed[i];
		}
	}

	public void randomize() {
		for (int i = 0; i < dimension; i++) {
			position[i] = X_MIN + random.nextDouble() * (X_MAX - X_MIN);
			bestPosition[i] = position[i];
			speed[i] = V_MIN + random.nextDouble() * (V_MAX - V_MIN);
		}
		bestPositionError = function.valueAt(bestPosition);
	}

	public int getDimension() {
		return dimension;
	}

	public List<Particle> getNeighborhoods() {
		return neighborhoods;
	}

	public double[] getPosition() {
		return position;
	}

	public void setNeighborhoods(List<Particle> neighborhoods) {
		this.neighborhoods = neighborhoods;
	}

	public double[] getBestPosition() {
		return bestPosition;
	}

	public void setBestPosition(double[] bestPosition) {
		this.bestPosition = bestPosition;
	}

	public double getBestPositionError() {
		return bestPositionError;
	}

	public void setBestPositionError(double bestPositionError) {
		this.bestPositionError = bestPositionError;
	}

	public double[] getBestNeighborhood() {
		int neighborhoodSize = neighborhoods.size();
		int bestIndex = 0;
		double currentBestError = neighborhoods.get(0).bestPositionError;
		for (int i = 1; i < neighborhoodSize; i++) {
			double tmpError = neighborhoods.get(i).bestPositionError;
			if (tmpError < currentBestError) {
				bestIndex = i;
				currentBestError = tmpError;
			}
		}
		return neighborhoods.get(bestIndex).bestPosition;
	}

}
