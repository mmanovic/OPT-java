package hr.fer.zemris.optjava.dz12.nodes;

import hr.fer.zemris.optjava.dz12.models.AntDirection;
import hr.fer.zemris.optjava.dz12.models.AntWorld;

public class RightNode extends AbstractNode {

	@Override
	public void execute(AntWorld world, boolean addToActionList) {
		if (world.movesLeft <= 0) {
			return;
		}
		if (addToActionList) {
			world.actionList.add(this);
		}
		switch (world.getDirection()) {
		case LEFT:
			world.setDirection(AntDirection.UP);
			break;
		case DOWN:
			world.setDirection(AntDirection.LEFT);
			break;
		case RIGHT:
			world.setDirection(AntDirection.DOWN);
			break;
		case UP:
			world.setDirection(AntDirection.RIGHT);
			break;
		}
		world.movesLeft--;
	}

	@Override
	public boolean isTerminal() {
		return true;
	}

	@Override
	public INode copy() {
		return new RightNode();
	}

	@Override
	public int childrenSize() {
		return 0;
	}

	@Override
	public String toString() {
		return "Right";
	}

}
