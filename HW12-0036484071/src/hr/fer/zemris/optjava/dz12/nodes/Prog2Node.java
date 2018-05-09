package hr.fer.zemris.optjava.dz12.nodes;

import hr.fer.zemris.optjava.dz12.models.AntWorld;

public class Prog2Node extends AbstractNode {

	@Override
	public void execute(AntWorld world, boolean addToActionList) {
		if (world.movesLeft <= 0) {
			return;
		}
		for (INode child : children) {
			child.execute(world, addToActionList);
		}
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public INode copy() {
		INode copy = new Prog2Node();
		for (INode child : children) {
			copy.addChild(child.copy());
		}
		return copy;
	}

	@Override
	public int childrenSize() {
		return 2;
	}

	@Override
	public String toString() {
		return "Prog2Node";
	}

}
