package hr.fer.zemris.optjava.dz4.part2.models;

public class Stick implements Comparable<Stick> {
	private int height;
	private int id;

	public Stick(int height, int id) {
		super();
		this.height = height;
		this.id = id;
	}

	public int getHeight() {
		return height;
	}

	public int getId() {
		return id;
	}

	public Stick duplicate() {
		return new Stick(height, id);
	}

	@Override
	public int compareTo(Stick arg0) {
		return Integer.compare(height, arg0.height);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stick other = (Stick) obj;
		if (id != other.id)
			return false;
		return true;
	}

}
