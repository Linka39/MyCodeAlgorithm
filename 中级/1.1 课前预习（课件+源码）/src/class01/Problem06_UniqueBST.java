package class01;

import java.util.List;
import java.util.LinkedList;

/**
 * 给定一个非负整数n，代表二叉树的节点个数。返回能形成多少种不同的二叉树结构
 */
public class Problem06_UniqueBST {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 思路：动态规划-递归方式，左子树的可形成的数目乘右子树数目 可以得出
	private static int getnumTrees(int n) {
		if (n < 0) {
			return 0;
		}
		if (n == 1 || n == 0) {
			return 1;
		}
		if (n == 2) {
			return 2;
		}
		int sum = 0;
		for (int i = 0; i < n ; i++) {
			int L = getnumTrees(i);
			int R = getnumTrees(n - i - 1);
			sum = sum + L * R;
		}
		return sum;
	}

	// 思路：动态规划    num[i]代表i个节点最多的摆法有多少
	public static int numTrees(int n) {
		if (n < 2) {
			return 1;
		}
		int[] num = new int[n + 1];
		num[0] = 1;
		for (int i = 1; i < n + 1; i++) {
			for (int j = 1; j < i + 1; j++) {
				num[i] += num[j - 1] * num[i - j];
			}
		}
		return num[n];
	}

	public static List<Node> generateTrees(int n) {
		return generate(1, n);
	}

	public static List<Node> generate(int start, int end) {
		List<Node> res = new LinkedList<Node>();
		if (start > end) {
			res.add(null);
		}
		Node head = null;
		for (int i = start; i < end + 1; i++) {
			head = new Node(i);
			List<Node> lSubs = generate(start, i - 1);
			List<Node> rSubs = generate(i + 1, end);
			for (Node l : lSubs) {
				for (Node r : rSubs) {
					head.left = l;
					head.right = r;
					res.add(cloneTree(head));
				}
			}
		}
		return res;
	}

	public static Node cloneTree(Node head) {
		if (head == null) {
			return null;
		}
		Node res = new Node(head.value);
		res.left = cloneTree(head.left);
		res.right = cloneTree(head.right);
		return res;
	}

	// for test -- print tree
	public static void printTree(Node head) {
		System.out.println("Binary Tree:");
		printInOrder(head, 0, "H", 17);
		System.out.println();
	}

	public static void printInOrder(Node head, int height, String to, int len) {
		if (head == null) {
			return;
		}
		printInOrder(head.right, height + 1, "v", len);
		String val = to + head.value + to;
		int lenM = val.length();
		int lenL = (len - lenM) / 2;
		int lenR = len - lenM - lenL;
		val = getSpace(lenL) + val + getSpace(lenR);
		System.out.println(getSpace(height * len) + val);
		printInOrder(head.left, height + 1, "^", len);
	}

	public static String getSpace(int num) {
		String space = " ";
		StringBuffer buf = new StringBuffer("");
		for (int i = 0; i < num; i++) {
			buf.append(space);
		}
		return buf.toString();
	}

	public static void main(String[] args) {
		int n = 4;
		System.out.println(numTrees(n));
		List<Node> res = generateTrees(n);
		for (Node node : res) {
			printTree(node);
		}
	}

}
