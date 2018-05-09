package hr.fer.zemris.optjava.dz12;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hr.fer.zemris.optjava.dz12.GAmodels.Chromosome;
import hr.fer.zemris.optjava.dz12.nodes.INode;
import hr.fer.zemris.optjava.dz12.nodes.IfFoodAheadNode;
import hr.fer.zemris.optjava.dz12.nodes.LeftNode;
import hr.fer.zemris.optjava.dz12.nodes.MoveNode;
import hr.fer.zemris.optjava.dz12.nodes.Prog2Node;
import hr.fer.zemris.optjava.dz12.nodes.Prog3Node;
import hr.fer.zemris.optjava.dz12.nodes.RightNode;

public class PopulationCreator {
	private int populationSize;
	private int maxInitDepth;
	private int maxNodes;
	private INode[] F = new INode[] { new IfFoodAheadNode(), new Prog2Node(), new Prog3Node() };
	private INode[] T = new INode[] { new MoveNode(), new LeftNode(), new RightNode() };
	private INode[] FTUnion = new INode[] { new IfFoodAheadNode(), new Prog2Node(), new Prog3Node(), new MoveNode(),
			new LeftNode(), new RightNode() };
	private Random random = new Random();

	public PopulationCreator(int populationSize, int maxInitDepth, int maxNodes) {
		super();
		this.populationSize = populationSize;
		this.maxInitDepth = maxInitDepth;
		this.maxNodes = maxNodes;
	}

	public List<Chromosome> createPopulation() {
		List<Chromosome> population = new ArrayList<>();
		int diffDepths = maxInitDepth - 1;
		int halfPopSize = populationSize / 2;
		for (int i = 0; i < halfPopSize; i++) {
			int depth = 2 + i % diffDepths;// mindepth+offset
			population.add(new Chromosome(createTree(depth, maxNodes, true)));
			population.add(new Chromosome(createTree(depth, maxNodes, false)));
		}
		return population;
	}

	public INode createTree(int maxDepth, int maxNodes, boolean grow) {
		if (maxDepth == 1) {
			return T[random.nextInt(T.length)].copy();
		}
		INode root = F[random.nextInt(F.length)].copy();
		maxDepth--;
		maxNodes--;
		for (int i = 0; i < root.childrenSize(); i++) {
			INode child = createSubTree(maxDepth, maxNodes, grow);
			maxNodes -= child.getSubTreeSize();
			root.addChild(child);
		}
		return root;
	}

	public INode createSubTree(int maxDepth, int maxNodes, boolean grow) {
		INode root;
		if (maxDepth == 1 || maxNodes < 1) {
			root = T[random.nextInt(T.length)].copy();
		} else if (!grow) {
			root = F[random.nextInt(F.length)].copy();
		} else {
			root = FTUnion[random.nextInt(FTUnion.length)].copy();
		}

		maxNodes--;
		for (int i = 0; i < root.childrenSize(); ++i) {
			INode child = createSubTree(maxDepth - 1, maxNodes, grow);
			maxNodes -= child.getSubTreeSize();
			root.addChild(child);
		}

		return root;
	}
}
