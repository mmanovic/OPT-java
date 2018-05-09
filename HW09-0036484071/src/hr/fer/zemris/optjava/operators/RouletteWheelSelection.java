package hr.fer.zemris.optjava.operators;

import java.util.List;
import java.util.Random;

public class RouletteWheelSelection implements ISelection {
	private Random random = new Random();

	@Override
	public Chromosome select(List<Chromosome> population) {
		int size = population.size();
		double fitnessWorst = Double.MAX_VALUE;
		double fitnessSum = 0;
		for (Chromosome x : population) {
			fitnessWorst = Double.min(fitnessWorst, x.getFitness());
			fitnessSum += x.getFitness();
		}
		fitnessSum -= size * fitnessWorst;
		double[] probabilities = new double[size];
		for (int i = 0; i < size; i++) {
			if (i == 0) {
				probabilities[i] = (population.get(i).getFitness() - fitnessWorst) / fitnessSum;
			} else {
				probabilities[i] = (population.get(i).getFitness() - fitnessWorst) / fitnessSum + probabilities[i - 1];
			}
		}
		double value = random.nextDouble();
		for (int i = 0; i < size; i++) {
			if (value < probabilities[i]) {
				return population.get(i);
			}
		}
		return null;
	}

}
