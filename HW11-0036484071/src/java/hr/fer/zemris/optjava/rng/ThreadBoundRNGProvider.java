package hr.fer.zemris.optjava.rng;

public class ThreadBoundRNGProvider implements IRNGProvider {
	@Override
	public IRNG getRNG() {
		return ((IRNGProvider) Thread.currentThread()).getRNG();
	}
}
