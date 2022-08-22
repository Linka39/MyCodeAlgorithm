package class05;

import java.util.LinkedList;

/**
 * 如何判断一颗二叉树是否是搜索二叉树？
 */
public class Code04_IsBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBST(Node head) {
		if (head == null) {
			return true;
		}
		// 思路：新增个链表记录中序遍历的节点，遍历链表，如果前一个节点大于后一个,则不是一个二叉搜索树
		LinkedList<Node> inOrderList = new LinkedList<>();
		process(head, inOrderList);
		int pre = Integer.MIN_VALUE;
		for (Node cur : inOrderList) {
			if (pre >= cur.value) {
				return false;
			}
			pre = cur.value;
		}
		return true;
	}

	public static void process(Node node, LinkedList<Node> inOrderList) {
		if (node == null) {
			return;
		}
		process(node.left, inOrderList);
		inOrderList.add(node);
		process(node.right, inOrderList);
	}

}
