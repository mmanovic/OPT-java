package hr.fer.zemris.optjava.dz8.neural;

import hr.fer.zemris.optjava.dz8.models.IDataset;

public abstract class AbstractANN {
	protected int[] layers;
	protected ITransferFunction[] transferFunctions;
	protected IDataset dataset;

	public AbstractANN(int[] layers, ITransferFunction[] transferFunctions, IDataset dataset) {
		super();
		if (layers.length - 1 != transferFunctions.length) {
			throw new IllegalArgumentException(
					"Number of transfer functions must be for one less from number of layers.");
		}
		this.layers = layers;
		this.transferFunctions = transferFunctions;
		this.dataset = dataset;
	}

	public double calcError(double[] weights) {
		int n = dataset.numberOfSamples();
		double error = 0;
		int outputSize = layers[layers.length - 1];

		for (int i = 0; i < n; i++) {
			double[] inputs = dataset.getInput(i);
			double[] outputs = new double[outputSize];
			calcOutputs(inputs, weights, outputs);
			double[] expected = dataset.getOutput(i);
			for (int j = 0; j < outputSize; j++) {
				error += Math.pow((expected[j] - outputs[j]), 2);
			}
		}
		return error / n;
	}

	public abstract void calcOutputs(double[] inputs, double[] weights, double[] outputs);

	public abstract int getWeightsCount();

}
