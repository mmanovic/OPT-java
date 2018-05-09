package hr.fer.zemris.generic.ga;

public abstract class GASolution<T> implements Comparable<GASolution<T>> {
	protected T data;
	public double fitness;

	public GASolution() {
	}

	public GASolution(T data, double fitness) {
		super();
		this.data = data;
		this.fitness = fitness;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public abstract GASolution<T> duplicate();

	public abstract int getNOfRectangles();

	@Override
	public int compareTo(GASolution<T> o) {
		return -Double.compare(this.fitness, o.fitness);
	}
}
