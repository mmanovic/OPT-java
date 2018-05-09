package hr.fer.zemris.optjava.dz7.models;

import java.util.List;

public class IrisDataset implements IDataset {
	List<InputData> data;

	public IrisDataset(List<InputData> data) {
		super();
		this.data = data;
	}

	@Override
	public int numberOfSamples() {
		return data.size();
	}

	@Override
	public InputData getData(int index) {
		if (index >= 0 && index < data.size()) {
			return data.get(index);
		} else {
			throw new IllegalArgumentException("Index is out of range.");
		}
	}

	@Override
	public double[] getInput(int index) {
		return getData(index).getInput();
	}

	@Override
	public double[] getOutput(int index) {
		return getData(index).getOutput();
	}
}
