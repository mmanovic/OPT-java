package hr.fer.zemris.optjava.dz12.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mato
 *
 */
public abstract class AbstractNode implements INode {
	protected List<INode> children;
	protected int depth;
	protected int subTreeSize;

	public AbstractNode() {
		super();
		this.children = new ArrayList<>();
		this.depth = 1;
		this.subTreeSize = 1;
	}

	public void addChild(INode node) {
		children.add(node);
		depth = -1;
		subTreeSize = -1;
	}

	public void setChild(int index, INode node) {
		children.set(index, node);
		depth = -1;
		subTreeSize = -1;
	}

	public void removeChild(int index) {
		children.remove(index);
		depth = -1;
		subTreeSize = -1;
	}

	public int getDepth() {
		if (depth != -1) {
			return depth;
		}
		depth = 0;
		for (INode child : children) {
			depth = Integer.max(depth, child.getDepth());
		}
		depth++;
		return depth;
	}

	public int getSubTreeSize() {
		if (subTreeSize != -1) {
			return subTreeSize;
		}
		subTreeSize = 1;
		for (INode child : children) {
			subTreeSize += child.getSubTreeSize();
		}
		return subTreeSize;
	}

	public List<INode> getChildren() {
		return children;
	}

	
	
	public INode getSubtree(int index) {
		if (index == 0)
			return this.copy();
		index--;
		int limit = childrenSize();
		for (int i = 0; i < limit; i++) {
			INode child = this.children.get(i);
			if (child.getSubTreeSize() > index) {
				return child.getSubtree(index);
			} else {
				index -= child.getSubTreeSize();
			}
		}
		return this.copy();
	}

	public void replaceSubtree(int index, INode newSubtree) {
		if (index == 0) {
			return;
		}
		index--;
		int limit = childrenSize();
		for (int i = 0; i < limit; i++) {
			INode child = children.get(i);
			if (index == 0) {
				setChild(i, newSubtree);
				return;
			}
			if (child.getSubTreeSize() > index) {
				child.replaceSubtree(index, newSubtree);
				return;
			} else {
				index -= child.getSubTreeSize();
			}
		}

	}

}
