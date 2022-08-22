package class05;

import java.util.LinkedList;

/**
 * 如何判断一颗二叉树是完全二叉树？
 */
public class Code05_IsCBT {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 思路：1、如果有右孩子节点，左孩子为空，则为不完全
	// 2、如果有一右孩子节点为空，之后的节点还有孩子，则为不完全
	public static boolean isCBT(Node head) {
		if (head == null) {
			return true;
		}
		LinkedList<Node> queue = new LinkedList<>();
		boolean leaf = false;
		Node l = null;
		Node r = null;
		queue.add(head);
		while (!queue.isEmpty()) {
			head = queue.poll();
			l = head.left;
			r = head.right;
			if ((leaf && (l != null || r != null)) || (l == null && r != null)) {
				return false;
			}
			if (l != null) {
				queue.add(l);
			}
			// leaf代表完全树的节点都进入队列，如果出队的节点再有孩子的话，说明不为完全二叉树
			if (r != null) {
				queue.add(r);
			} else {
				leaf = true;
			}
		}
		return true;
	}

}
