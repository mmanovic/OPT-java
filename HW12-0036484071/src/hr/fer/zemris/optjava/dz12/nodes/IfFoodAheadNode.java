package hr.fer.zemris.optjava.dz12.nodes;

import hr.fer.zemris.optjava.dz12.models.AntWorld;

public class IfFoodAheadNode extends AbstractNode {

	@Override
	public void execute(AntWorld world, boolean addToActionList) {
		if (world.movesLeft > 0) {
			if (world.foodAhead()) {
				children.get(0).execute(world, addToActionList);
			} else {
				children.get(1).execute(world, addToActionList);
			}
		}
	}

	@Override
	public boolean isTerminal() {
		return false;
	}

	@Override
	public INode copy() {
		INode copy = new IfFoodAheadNode();
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
		return "IfFoodAheadNode";
	}

}
