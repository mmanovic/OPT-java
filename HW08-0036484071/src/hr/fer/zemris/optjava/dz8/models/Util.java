package hr.fer.zemris.optjava.dz8.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {
	public static IDataset loadDataset(Path file, int inputSize, int nOfSamples) {
		List<InputData> data = new ArrayList<>();
		if (!Files.exists(file)) {
			System.out.println("File " + file.toString() + " doesn't exist.");
			System.exit(1);
		}
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(file);
		} catch (IOException e) {
			System.out.println("Unable to open file.");
			System.exit(1);
		}

		if (nOfSamples > lines.size()) {
			throw new IllegalArgumentException();
		}
		if (nOfSamples == -1) {
			nOfSamples = lines.size();
		}

		double[] samples = new double[lines.size()];
		int i = 0;
		double maxInput = -Double.MAX_VALUE;
		double minInput = Double.MAX_VALUE;
		for (String line : lines) {
			samples[i] = Double.parseDouble(line);
			maxInput = Double.max(maxInput, samples[i]);
			minInput = Double.min(minInput, samples[i]);
			i++;
		}
		for (i = 0; i < samples.length; i++) {
			samples[i] = 2 * (samples[i] - minInput) / (maxInput - minInput) - 1.0;
		}

		for (i = 0; i < nOfSamples - inputSize; i++) {
			double[] input = new double[inputSize];
			double[] output = new double[1];
			for (int j = 0; j < inputSize; j++) {
				input[j] = samples[i + j];
			}
			output[0] = samples[i + inputSize];
			data.add(new InputData(input, output));
		}

		return new LaserDataset(data);
	}
}
