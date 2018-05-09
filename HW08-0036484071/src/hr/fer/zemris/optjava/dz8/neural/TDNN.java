package hr.fer.zemris.optjava.dz8.neural;

import hr.fer.zemris.optjava.dz8.models.IDataset;

public class TDNN extends AbstractANN {

	private int weightsCount;

	public TDNN(int[] layers, ITransferFunction[] transferFunctions, IDataset dataset) {
		super(layers, transferFunctions, dataset);
		weightsCount = 0;
		for (int i = 1; i < layers.length; i++) {
			weightsCount += layers[i] * layers[i - 1] + layers[i];
		}
		
	}

	public int getWeightsCount() {
		return weightsCount;
	}

	public void calcOutputs(double[] inputs, double[] weights, double[] outputs) {
		if (inputs.length != layers[0] || weights.length != getWeightsCount()
				|| outputs.length != layers[layers.length - 1]) {
			throw new IllegalArgumentException("Arguments are not valid.");
		}

		double[] lastOutput = new double[layers[0]];
		System.arraycopy(inputs, 0, lastOutput, 0, inputs.length);
		int weightPointer = 0;
		for (int i = 1; i < layers.length; i++) {
			double[] currentOutput = new double[layers[i]];
			for (int j = 0; j < layers[i]; j++) {
				double result = 0;
				for (int k = 0; k < layers[i - 1]; k++) {
					result += weights[weightPointer++] * lastOutput[k];
				}
				result += weights[weightPointer++];
				currentOutput[j] = transferFunctions[i - 1].valueAt(result);
			}
			lastOutput = currentOutput;
		}
		System.arraycopy(lastOutput, 0, outputs, 0, lastOutput.length);
	}

}
