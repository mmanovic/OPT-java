package hr.fer.zemris.optjava.dz7;

import java.nio.file.Paths;

import hr.fer.zemris.optjava.dz7.models.FFANN;
import hr.fer.zemris.optjava.dz7.models.IDataset;
import hr.fer.zemris.optjava.dz7.models.ITransferFunction;
import hr.fer.zemris.optjava.dz7.models.SigmoidFunction;
import hr.fer.zemris.optjava.dz7.models.Util;

public class Test {

	public static void main(String[] args) {
		IDataset dataset = Util.loadDataset(Paths.get("./07-iris-formatirano.data"));
		System.out.println("Imamo uzoraka za ucenje: " + dataset.numberOfSamples());

		FFANN ffann = new FFANN(new int[] { 4, 5, 3, 3 },
				new ITransferFunction[] { new SigmoidFunction(), new SigmoidFunction(), new SigmoidFunction() },
				dataset);
		System.out.println(ffann.getWeightsCount());
		int weightsCounter = ffann.getWeightsCount();
		double[] weights = new double[weightsCounter];
		for (int i = 0; i < weightsCounter; i++) {
			weights[i] = 0.1;
		}
		System.out.println(ffann.calcError(weights));
		System.out.println("=====================");

		ffann = new FFANN(new int[] { 4, 3, 3 },
				new ITransferFunction[] { new SigmoidFunction(), new SigmoidFunction() }, dataset);
		System.out.println(ffann.getWeightsCount());
		weightsCounter = ffann.getWeightsCount();
		weights = new double[weightsCounter];
		for (int i = 0; i < weightsCounter; i++) {
			weights[i] = -0.2;
		}
		System.out.println(ffann.calcError(weights));
	}
}
