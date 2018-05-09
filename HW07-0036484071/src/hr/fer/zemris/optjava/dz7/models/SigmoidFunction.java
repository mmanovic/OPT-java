package hr.fer.zemris.optjava.dz7.models;

public class SigmoidFunction implements ITransferFunction {

	
	@Override
	public double valueAt(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}

}
