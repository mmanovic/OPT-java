package hr.fer.zemris.optjava.dz7.pso;

import hr.fer.zemris.optjava.dz7.models.FFANN;
import hr.fer.zemris.optjava.dz7.models.FFANNFunction;
import hr.fer.zemris.optjava.dz7.models.IFunction;

public class PSO {
	private Swarm swarm;
	private double merr;
	private int maxIteration;
	private IFunction function;

	private int dimension;
	public static double W_MAX = 1;
	public static double W_MIN = 0;

	public PSO(int populationSize, double merr, int maxIteration, int neighborhoodSize, FFANN ffann) {
		super();
		this.merr = merr;
		this.maxIteration = maxIteration;
		this.dimension = ffann.getWeightsCount();
		this.function = new FFANNFunction(ffann);
		swarm = new Swarm(populationSize, neighborhoodSize, ffann.getWeightsCount(), function);
	}

	public double[] run() {
		int iteration = 0;
		double error = Double.MAX_VALUE;
		double[] bestWeights = new double[dimension];
		while (iteration < maxIteration && error > merr) {
			iteration++;
			swarm.updateParticlesSpeed(W_MAX - (1.0 * iteration / maxIteration) * (W_MAX - W_MIN));
			for (Particle particle : swarm) {
				double[] particlePosition = particle.getPosition();
				double particleError = function.valueAt(particlePosition);
				if (particleError < error) {
					error = particleError;
					System.arraycopy(particlePosition, 0, bestWeights, 0, dimension);
				}
				if (particleError < particle.getBestPositionError()) {
					System.arraycopy(particlePosition, 0, particle.getBestPosition(), 0, dimension);
					particle.setBestPositionError(particleError);
				}
			}
			if (iteration % 100 == 0) {
				System.out.println("Iteration " + iteration + " Current error: " + error);
			}
		}
		System.out.println("Final solution: ");
		for (int i = 0; i < dimension; i++) {
			System.out.print(bestWeights[i] + " ");
		}
		System.out.println("] ");
		System.out.println("Error: " + error);
		return bestWeights;
	}
}
