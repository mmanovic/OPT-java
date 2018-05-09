package hr.fer.zemris.optjava.dz8.neural;

public class TANHFunction implements ITransferFunction {

	@Override
	public double valueAt(double x) {
		return Math.tanh(x);
	}

}
