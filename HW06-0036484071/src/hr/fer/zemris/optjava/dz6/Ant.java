package hr.fer.zemris.optjava.dz6;

import java.util.List;

public class Ant {
	private double distance;
	private List<Integer> path;

	public Ant(double distance, List<Integer> path) {
		super();
		this.distance = distance;
		this.path = path;
	}

	public double getDistance() {
		return distance;
	}

	public List<Integer> getPath() {
		return path;
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("Distance: " + distance + " Path: [");
		for (Integer entry : path) {
			string.append(entry + " ");
		}
		string.append("]");
		return string.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((path == null) ? 0 : path.hashCode());
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
		Ant other = (Ant) obj;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}

}
