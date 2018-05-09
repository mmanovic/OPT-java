package hr.fer.zemris.optjava.dz12.GAmodels;

import java.util.Random;

import hr.fer.zemris.optjava.dz12.nodes.INode;

public class OnePointCrossover implements ICrossover {
	private int maxDepth;
	private int maxNodes;
	private int maxEffort;
	private Random random = new Random();

	public OnePointCrossover(int maxDepth, int maxNodes, int maxEffort) {
		super();
		this.maxDepth = maxDepth;
		this.maxNodes = maxNodes;
		this.maxEffort = maxEffort;
	}

	@Override
	public Chromosome cross(Chromosome x, Chromosome y) {
		INode xRoot = x.getRoot();
		INode yRoot = y.getRoot();
		for (int i = 0; i < maxEffort; i++) {
			INode child = xRoot.copy();
			child.replaceSubtree(random.nextInt(xRoot.getSubTreeSize()),
					yRoot.getSubtree(random.nextInt(yRoot.getSubTreeSize())));
			if (child.getSubTreeSize() <= maxNodes && child.getDepth() <= maxDepth) {
				return new Chromosome(child);
			}
		}
		return new Chromosome(xRoot.copy());
	}

}
