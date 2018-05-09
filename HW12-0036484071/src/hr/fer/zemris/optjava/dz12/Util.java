package hr.fer.zemris.optjava.dz12;

import hr.fer.zemris.optjava.dz12.nodes.INode;

public class Util {
	public static String printTree(INode root, int depth, StringBuilder sb) {
		for (int i = 0; i < depth; i++) {
			sb.append(" ");
		}
		sb.append(root.toString() + "\n");
		for (INode child : root.getChildren()) {
			printTree(child, depth + 1, sb);
		}
		return sb.toString();
	}
}
