package hr.fer.zemris.optjava.dz3.decoders;

public interface IDecoder<T> {
	public double[] decode(T solution);

	public void decode(T solution, double[] values);
}
