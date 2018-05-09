package hr.fer.zemris.optjava.dz3.algorithms;

import hr.fer.zemris.optjava.dz3.decoders.IDecoder;
import hr.fer.zemris.optjava.dz3.functions.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhoods.INeighborhood;

public class GreedyAlgorithm<T> implements IOptAlgorithm<T> {
	private static int MAX_ITERATIONS = 20000;
	private IDecoder<T> decoder;
	private IFunction function;
	private boolean minimize;
	private INeighborhood<T> neighborhood;
	private T startWith;

	public GreedyAlgorithm(IDecoder<T> decoder, IFunction function, boolean minimize, INeighborhood<T> neighborhood,
			T startWith) {
		super();
		this.decoder = decoder;
		this.function = function;
		this.minimize = minimize;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
	}

	@Override
	public void run() {
		T currentSolution = startWith;
		double[] vector = decoder.decode(startWith);
		double fitness = -function.valueAt(vector);
		double[] neighborhoodVector;
		for (int i = 0; i < MAX_ITERATIONS; i++) {
			T neighborhoodSolution = neighborhood.randomNeighbor(currentSolution);
			neighborhoodVector = decoder.decode((neighborhoodSolution));
			double neighborhoodFitness = -function.valueAt(neighborhoodVector);
			if (minimize) {
				if (neighborhoodFitness >= fitness) {
					currentSolution = neighborhoodSolution;
				}
			} else {
				if (neighborhoodFitness < fitness) {
					currentSolution = neighborhoodSolution;
				}
			}
		}
		System.out.println("Final solution:");
		neighborhoodVector = decoder.decode(currentSolution);
		for (double element : neighborhoodVector) {
			System.out.print(element + " ");
		}
		System.out.println("\nValue error: " + function.valueAt(neighborhoodVector));
	}

}
