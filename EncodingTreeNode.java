//David made this entire file

import java.io.Serializable;

public class EncodingTreeNode implements Comparable<EncodingTreeNode>, Serializable {
	EncodingTreeNode left;
	EncodingTreeNode right;
	int priority;
	char element;
	
	public EncodingTreeNode() {
		left = null;
		right = null;
		priority = 0;
	}
	
	public EncodingTreeNode(char ch) {
		this(ch, 1);
	}
	
	/**
	 * Encoding Tree Node used to store a single character
	 * @param ch
	 * @param priority
	 */
	public EncodingTreeNode(char ch, int priority) {
		left = null;
		right = null;
		this.priority = priority;
		this.element = ch;
	}
	
	/**
	 * Encoding Tree Node used to combine two other Encoding Tree Nodes
	 * @param left
	 * @param right
	 */
	public EncodingTreeNode(EncodingTreeNode left, EncodingTreeNode right) {
		this.left = left;
		this.right = right;
		priority = left.priority + right.priority;
	}
	
	public int compareTo(EncodingTreeNode node) {
		if (priority > node.priority) {
			return 1;
		} else if (priority == node.priority) {
			return 0;
		} else {
			return -1;
		}
	}
	
}
