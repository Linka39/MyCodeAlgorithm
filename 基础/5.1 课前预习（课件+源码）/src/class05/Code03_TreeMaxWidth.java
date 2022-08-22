package class05;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * 求一棵二叉树的最大宽度
 */
public class Code03_TreeMaxWidth {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static int getMaxWidth(Node head) {
		if (head == null) {
			return 0;
		}
		int maxWidth = 0;
		int curWidth = 0;
		int curLevel = 0;
		// 记录树层级的HashMap
		HashMap<Node, Integer> levelMap = new HashMap<>();
		levelMap.put(head, 1);
		// 实例化一个队列用于层次遍历树
		LinkedList<Node> queue = new LinkedList<>();
		queue.add(head);
		Node node = null;
		Node left = null;
		Node right = null;
		while (!queue.isEmpty()) {
			// 先进先出，先把根节点取出
			node = queue.poll();
			left = node.left;
			right = node.right;
			// 每个孩子节点放入层级map时，都要在父节点基础上加1
			if (left != null) {
				levelMap.put(left, levelMap.get(node) + 1);
				queue.add(left);
			}
			if (right != null) {
				levelMap.put(right, levelMap.get(node) + 1);
				queue.add(right);
			}
			// 判断是否为新的一层，更新当前宽度，当前层级（出队的节点，所在层级大于当前层级）
			if (levelMap.get(node) > curLevel) {
				curWidth = 0;
				curLevel = levelMap.get(node);
			// 不是新的一层，当前宽度加1
			} else {
				curWidth++;
			}
			// 每次层级遍历都得出一个最大宽度
			maxWidth = Math.max(maxWidth, curWidth);
		}
		return maxWidth;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
