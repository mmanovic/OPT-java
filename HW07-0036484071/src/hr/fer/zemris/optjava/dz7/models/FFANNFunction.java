package hr.fer.zemris.optjava.dz7.models;

public class FFANNFunction implements IFunction{
	private final FFANN net;

	public FFANNFunction(FFANN net) {
		this.net = net;
	}

	public double valueAt(double[] weights) {
		return net.calcError(weights);
	}
}
