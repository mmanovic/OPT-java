package hr.fer.zemris.optjava.dz7.models;

public interface IDataset {
	public int numberOfSamples();
	
	public InputData getData(int index);

	public double[] getInput(int index);

	public double[] getOutput(int index);
}
