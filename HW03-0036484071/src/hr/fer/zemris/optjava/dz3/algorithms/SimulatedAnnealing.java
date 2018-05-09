package hr.fer.zemris.optjava.dz3.algorithms;

import java.util.Random;

import hr.fer.zemris.optjava.dz3.decoders.IDecoder;
import hr.fer.zemris.optjava.dz3.functions.IFunction;
import hr.fer.zemris.optjava.dz3.neighborhoods.INeighborhood;
import hr.fer.zemris.optjava.dz3.schedules.ITempSchedules;

public class SimulatedAnnealing<T> implements IOptAlgorithm<T> {
	private IDecoder<T> decoder;
	private IFunction function;
	private boolean minimize;
	private INeighborhood<T> neighborhood;
	private T startWith;
	private Random random;
	private ITempSchedules schedule;

	public SimulatedAnnealing(IDecoder<T> decoder, IFunction function, boolean minimize, INeighborhood<T> neighborhood,
			T startWith, ITempSchedules schedule) {
		super();
		this.decoder = decoder;
		this.function = function;
		this.minimize = minimize;
		this.neighborhood = neighborhood;
		this.startWith = startWith;
		this.schedule = schedule;
		random = new Random();
	}

	@Override
	public void run() {
		T currentSolution = startWith;
		int outerLimit = schedule.getOuterLoopCounter();
		int innerLimit = schedule.getInnerLoopCounter();
		for (int i = 0; i < outerLimit; i++) {
			double temperature = schedule.getNextTemperature();
			for (int j = 0; j < innerLimit; j++) {
				T neighborhoodSolution = neighborhood.randomNeighbor(currentSolution);
				double currentValue = function.valueAt(decoder.decode(currentSolution));
				double neighborhoodValue = function.valueAt(decoder.decode(neighborhoodSolution));
				double difference = 0;
				if (minimize) {
					difference = neighborhoodValue - currentValue;

				} else {
					difference = currentValue - neighborhoodValue;
				}
				if (difference <= 0) {
					currentSolution = neighborhoodSolution;
				} else {
					if (random.nextDouble() < Math.exp(-difference / temperature)) {
						currentSolution = neighborhoodSolution;
					}
				}
				double[] solutionVector = decoder.decode(currentSolution);
				System.out.print("Temperature: " + temperature + " ");
//				for (double element : decoder.decode(currentSolution)) {
//					System.out.print(element + " ");
//				}
				System.out.println("Value error: " + function.valueAt(solutionVector));
			}
		}
		System.out.println("Final solution:");
		for (double element : decoder.decode(currentSolution)) {
			System.out.print(element + " ");
		}

	}
}
