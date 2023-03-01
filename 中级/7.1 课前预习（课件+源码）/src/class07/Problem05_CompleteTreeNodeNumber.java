package class07;

/**
 * 求完全二叉树节点的个数
 */
public class Problem05_CompleteTreeNodeNumber {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 思路：满二叉树高度为 n，最后一层节点数为 2(n-1) 个。总节点数为 2(n-1)*2-1 个
	public static int nodeNum(Node head) {
		if (head == null) {
			return 0;
		}
		return bs(head, 1, mostLeftLevel(head, 1));
	}

	public static int bs(Node node, int l, int h) {
		if (l == h) {
			return 1;
		}
		// 如果右子树的最大高度等于总高度的话，说明此时的右子树并没有缺少。则前面的节点可当作满二叉树相加
		if (mostLeftLevel(node.right, l + 1) == h) {
			return (1 << (h - l)) + bs(node.right, l + 1, h);
		// 如果右子树的最大高度不等于总高度的话，说明此时的右子树有缺少。则前面的节点也可当作满二叉树相加
		} else {
			return (1 << (h - l - 1)) + bs(node.left, l + 1, h);
		}
	}

	// 求树的最左子树的深度，对于完全二叉树来说就是树的高度
	public static int mostLeftLevel(Node node, int level) {
		while (node != null) {
			level++;
			node = node.left;
		}
		return level - 1;
	}

	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(2);
		head.right = new Node(3);
		head.left.left = new Node(4);
		head.left.right = new Node(5);
		head.right.left = new Node(6);
		System.out.println(nodeNum(head));

	}

}
