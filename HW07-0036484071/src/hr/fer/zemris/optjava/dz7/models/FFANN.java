package hr.fer.zemris.optjava.dz7.models;

public class FFANN {
	private int[] layers;
	private ITransferFunction[] transferFunctions;
	private IDataset dataset;
	private int weightsCount;

	public FFANN(int[] layers, ITransferFunction[] transferFunctions, IDataset dataset) {
		super();
		this.layers = layers;
		this.transferFunctions = transferFunctions;
		this.dataset = dataset;
		if (layers.length - 1 != transferFunctions.length) {
			throw new IllegalArgumentException(
					"Number of transfer functions must be for one less from number of layers.");
		}
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

	private boolean isSimilar(double[] outputs, double[] expected) {
		double EPSILON = 1E-9;
		for (int i = 0; i < outputs.length; i++) {
			if (Math.abs(outputs[i] - expected[i]) > EPSILON) {
				return false;
			}
		}
		return true;
	}

	public void printStats(double[] weights) {
		int nOfSamples = dataset.numberOfSamples();
		int outputSize = layers[layers.length - 1];
		int accuracy = 0;
		for (int i = 0; i < nOfSamples; i++) {
			double[] inputs = dataset.getInput(i);
			double[] outputs = new double[outputSize];
			double[] expected = dataset.getOutput(i);
			calcOutputs(inputs, weights, outputs);
			for (int j = 0; j < outputSize; j++) {
				if (outputs[j] < 0.5) {
					outputs[j] = 0;
				} else {
					outputs[j] = 1;
				}
			}
			if (!isSimilar(outputs, expected)) {
				System.out.println("Uzorak " + (i + 1));
				System.out.print("Expected: [");
				for (int j = 0; j < outputSize; j++) {
					System.out.print(expected[j] + " ");
				}
				System.out.print("] Result: [");
				for (int j = 0; j < outputSize; j++) {
					System.out.print(outputs[j] + " ");
				}
				System.out.println("]");
			} else {
				accuracy++;
			}
		}
		System.out.println("Accuracy score: " + accuracy + "/" + nOfSamples);
	}

}
