package class05;

/**
 * 如何判断一颗二叉树是否是满二叉树？
 */
public class Code06_IsBalancedTree {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	public static boolean isBalanced(Node head) {
		return process(head).isBalanced;
	}

	// 二叉树固定套路：树形DP(动态规划)
	// 将判断问题进行拆分，如果父问题的判断依靠子问题的结果并且对每个子问题的结果集要求相似，那么将子问题封装成一个黑盒
	// 1、左右子树平衡，2、左右子树平衡高度相差小于2
	public static class ReturnType {
		public boolean isBalanced;
		public int height;

		public ReturnType(boolean isB, int hei) {
			isBalanced = isB;
			height = hei;
		}
	}

	public static ReturnType process(Node x) {
		if (x == null) {
			return new ReturnType(true, 0);
		}
		ReturnType leftData = process(x.left);
		ReturnType rightData = process(x.right);
		// 根据前两个子树得到当前树的高度，是否平衡
		int height = Math.max(leftData.height, rightData.height) + 1;
		boolean isBalanced = leftData.isBalanced && rightData.isBalanced
				&& Math.abs(leftData.height - rightData.height) < 2;
		return new ReturnType(isBalanced, height);
	}

	// 满二叉树的判断
	public static class MBT_ReturnType{
		int height;
		int count;
		public MBT_ReturnType(int h, int c) {
			height = h;
			count = c;
		}
	}
	// 采取树的节点数目和高度，如果 (节点树 == 2^高度 - 1)，那么为满二叉树
	public static MBT_ReturnType isMBTree(Node head) {
		if (head == null){
			return new MBT_ReturnType(0, 0);
		}
		MBT_ReturnType left = isMBTree(head.left);
		MBT_ReturnType right = isMBTree(head.right);
		int count = left.count + right.count + 1;
		int height = Math.max(left.height, right.height) + 1;

		return new MBT_ReturnType(height, count);
	}
	public static boolean isMBTree_bool(Node head) {
		MBT_ReturnType result = isMBTree(head);
		return Math.pow(2, result.height) - 1 == result.count;
	}


	public static void main(String[] args) {
		Node head = new Node(1);
		head.left = new Node(3);
		head.right = new Node(8);
		head.left.left = new Node(2);
		head.left.right = new Node(4);
		head.left.left.left = new Node(1);
		head.left.left.right = new Node(1);
		head.left.right.left = new Node(9);
		head.left.right.right = new Node(11);
		head.right.left = new Node(7);
		head.right.right = new Node(10);
		head.right.left.left = new Node(6);
		head.right.left.right = new Node(6);
		head.right.right.left = new Node(9);
		head.right.right.right = new Node(11);
//        head.right.right.right.right = new Node(11);
//        head.right.right.right.right.right = new Node(11);
		System.out.println("isMBTree_bool: " + isMBTree_bool(head));

	}

}
