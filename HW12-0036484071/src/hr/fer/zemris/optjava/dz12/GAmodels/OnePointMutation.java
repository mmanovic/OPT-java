package hr.fer.zemris.optjava.dz12.GAmodels;

import java.util.Random;

import hr.fer.zemris.optjava.dz12.PopulationCreator;
import hr.fer.zemris.optjava.dz12.nodes.INode;

public class OnePointMutation implements IMutation {
	private int maxDepth;
	private int maxNodes;
	private Random random = new Random();
	private PopulationCreator creator = new PopulationCreator(0, 0, 0);

	public OnePointMutation(int maxDepth, int maxNodes) {
		super();
		this.maxDepth = maxDepth;
		this.maxNodes = maxNodes;
	}

	@Override
	public Chromosome mutate(Chromosome x) {
		INode xRoot = x.getRoot();
		int index = random.nextInt(xRoot.getSubTreeSize());
		INode subtree = xRoot.getSubtree(index);
		// slucajno odabrano podstablo zamijenimo s novogeneriranim podstablom
		// slucajno odabrane dubine i metode stvaranja
		INode newSubtree = creator.createTree(
				Math.max(random.nextInt(Math.max(maxDepth - (xRoot.getDepth() - subtree.getDepth()), 1)), 1),
				maxNodes - (xRoot.getSubTreeSize() - subtree.getSubTreeSize()), random.nextBoolean());
		INode child = xRoot.copy();
		child.replaceSubtree(index, newSubtree);
		return new Chromosome(child);

	}

}
