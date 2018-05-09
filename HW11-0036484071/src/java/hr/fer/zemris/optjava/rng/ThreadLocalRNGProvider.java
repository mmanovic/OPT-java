package hr.fer.zemris.optjava.rng;

public class ThreadLocalRNGProvider implements IRNGProvider {
	private ThreadLocal<IRNG> threadLocal = new ThreadLocal<>();

	@Override
	public IRNG getRNG() {
		IRNG irng = threadLocal.get();
		if (irng == null) {
			irng = new RNGRandomImpl();
			threadLocal.set(irng);
		}
		return irng;
	}
}
