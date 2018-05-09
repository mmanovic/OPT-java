package hr.fer.zemris.optjava.dz4.part2.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Bin implements Iterable<Stick> {
	private int maxHeight = 20;
	private int restSpace;
	private List<Stick> sticks;

	public Bin(int maxHeight) {
		this.maxHeight = maxHeight;
		restSpace = maxHeight;
		sticks = new ArrayList<>();
	}

	public Bin(int maxHeight, List<Stick> sticks) {
		super();
		this.maxHeight = maxHeight;
		this.sticks = sticks;
		int heightsSum = 0;
		for (Stick stick : sticks) {
			heightsSum += stick.getHeight();
		}
		restSpace = maxHeight - heightsSum;
	}

	public Bin duplicate() {
		List<Stick> duplicate = new ArrayList<>();
		for (Stick stick : sticks) {
			duplicate.add(stick.duplicate());
		}
		return new Bin(maxHeight, duplicate);
	}

	public boolean addStick(Stick stick) {
		int height = stick.getHeight();
		if (height <= restSpace) {
			sticks.add(stick);
			restSpace -= height;
			return true;
		} else {
			return false;
		}
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public int getRestSpace() {
		return restSpace;
	}

	public List<Stick> getSticks() {
		return sticks;
	}

	@Override
	public Iterator<Stick> iterator() {
		return sticks.iterator();
	}

	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		string.append("[");
		for (Stick stick : sticks) {
			string.append(stick.getHeight() + " ");
		}
		string.deleteCharAt(string.length() - 1);
		return string.append("]").toString();
	}

}
