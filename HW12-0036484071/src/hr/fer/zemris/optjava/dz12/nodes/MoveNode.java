package hr.fer.zemris.optjava.dz12.nodes;

import hr.fer.zemris.optjava.dz12.models.AntWorld;

public class MoveNode extends AbstractNode {

	@Override
	public void execute(AntWorld world, boolean addToActionList) {
		if (world.movesLeft <= 0) {
			return;
		}
		if (addToActionList) {
			world.actionList.add(this);
		}
		world.move();
		world.movesLeft--;
	}

	@Override
	public boolean isTerminal() {
		return true;
	}

	@Override
	public INode copy() {
		return new MoveNode();
	}

	@Override
	public int childrenSize() {
		return 0;
	}

	@Override
	public String toString() {
		return "Move";
	}
}
