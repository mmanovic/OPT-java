package hr.fer.zemris.optjava.dz7.models;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Util {
	public static IDataset loadDataset(Path file) {
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

		for (String line : lines) {
			String[] tmp = line.split(":");
			tmp[0] = tmp[0].substring(1, tmp[0].length() - 1);
			tmp[1] = tmp[1].substring(1, tmp[1].length() - 1);
			String[] inputString = tmp[0].split(",");
			String[] outputString = tmp[1].split(",");
			int inputLen = inputString.length;
			int outputLen = outputString.length;
			double[] input = new double[inputLen];
			double[] output = new double[outputLen];
			for (int i = 0; i < inputLen; i++) {
				input[i] = Double.parseDouble(inputString[i]);
			}
			for (int i = 0; i < outputLen; i++) {
				output[i] = Double.parseDouble(outputString[i]);
			}
			data.add(new InputData(input, output));
		}

		return new IrisDataset(data);
	}
}
