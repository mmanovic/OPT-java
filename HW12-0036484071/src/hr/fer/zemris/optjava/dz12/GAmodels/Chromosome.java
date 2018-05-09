package hr.fer.zemris.optjava.dz12.GAmodels;

import hr.fer.zemris.optjava.dz12.models.AntWorld;
import hr.fer.zemris.optjava.dz12.nodes.INode;

public class Chromosome implements Comparable<Chromosome> {
	private INode root;
	private double fitness;

	public Chromosome(INode root) {
		super();
		this.root = root;
	}

	public INode getRoot() {
		return root;
	}

	public void setRoot(INode root) {
		this.root = root;
	}

	public double getFitness() {
		return fitness;
	}

	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public void evaluate(AntWorld world, boolean addToActionList) {
		while (world.movesLeft > 0) {
			root.execute(world, addToActionList);
		}
		fitness = world.foodEaten;
	}

	public Chromosome copy() {
		Chromosome copy = new Chromosome(root.copy());
		copy.setFitness(fitness);
		return copy;
	}

	@Override
	public int compareTo(Chromosome arg0) {
		return Double.compare(fitness, arg0.fitness);
	}

}
