package class05;

/**
 * Morris遍历  
 * 一种遍历二叉树的方式，并且时间复杂度O(N)，额外空间复杂度O(1)  
 * 通过利用原树中大量空闲指针的方式，达到节省空间的目的
 *
 * Morris遍历细节   假设来到当前节点cur，开始时cur来到头节点位置
 * 1）如果cur没有左孩子，cur向右移动(cur = cur.right)
 * 2）如果cur有左孩子，找到左子树上最右的节点mostRight：
 * 		a.如果mostRight的右指针指向空，让其指向cur， 然后cur向左移动(cur = cur.left)
 * 		b.如果mostRight的右指针指向cur，让其指向null， 然后cur向右移动(cur = cur.right)
 * 3）cur为空时遍历停止
 */
public class Code01_MorrisTraversal {

	// 定义树的节点
	public static class Node {
		public int value;
		Node left;
		Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// morris中序遍历
	public static void morrisIn(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head; // 记录当前遍历节点
		Node cur2 = null; // 记录最右节点
		while (cur1 != null) {
			cur2 = cur1.left;
			// 如果cur有左孩子，找到左子树上最右的节点mostRight：
			if (cur2 != null) {
				// 右孩子节点不为空且不指向父节点时
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				// 左孩子右节点为空时，将他指向父节点，cur1移到左节点
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				// 此时左孩子右节点指向了父节点，将他进行还原
				} else {
					cur2.right = null;
				}
			}
			// 中序遍历的打印
			System.out.print(cur1.value + " ");
			cur1 = cur1.right;
		}
		System.out.println();
	}

	public static void morrisPre(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head; // 记录当前遍历节点
		Node cur2 = null; // 记录最右节点
		while (cur1 != null) {
			cur2 = cur1.left;
			// 如果cur有左孩子，找到左子树上最右的节点mostRight：
			if (cur2 != null) {
				// 右孩子节点不为空且不指向父节点时
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				// 左孩子右节点为空时，将他指向父节点，打印cur1，并移到左节点
				if (cur2.right == null) {
					cur2.right = cur1;
					System.out.print(cur1.value + " ");
					cur1 = cur1.left;
					continue;
				// 此时左孩子右节点指向了父节点，将他进行还原
				} else {
					cur2.right = null;
				}
			} else {
				// 左孩子为空时也进行一次打印
				System.out.print(cur1.value + " ");
			}
			cur1 = cur1.right;
		}
		System.out.println();
	}

	public static void morrisPos(Node head) {
		if (head == null) {
			return;
		}
		Node cur1 = head; // 记录当前遍历节点
		Node cur2 = null; // 记录最右节点
		while (cur1 != null) {
			// 如果cur有左孩子，找到左子树上最右的节点mostRight：
			cur2 = cur1.left;
			if (cur2 != null) {
				// 右孩子节点不为空且不指向父节点时
				while (cur2.right != null && cur2.right != cur1) {
					cur2 = cur2.right;
				}
				// 左孩子右节点为空时，将他指向父节点，并移到左节点
				if (cur2.right == null) {
					cur2.right = cur1;
					cur1 = cur1.left;
					continue;
				} else {
					// 左孩子右节点指向了父节点，此时逆序打印此节点的左孩子的右节点，实现左孩子节点的后续遍历
					cur2.right = null;
					printEdge(cur1.left);
				}
			}
			// 处理右孩子
			cur1 = cur1.right;
		}
		// 将右孩子所在的那一链逆序打印
		printEdge(head);
		System.out.println();
	}

	// 将head节点逆序打印，并还原head节点
	public static void printEdge(Node head) {
		Node tail = reverseEdge(head);
		Node cur = tail;
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.right;
		}
		reverseEdge(tail);
	}

	// 将from节点后的右孩子节点进行逆序操作，并返回尾节点
	public static Node reverseEdge(Node from) {
		Node pre = null;
		Node next = null;
		// 下个节点为右孩子
		while (from != null) {
			next = from.right;
			from.right = pre;
			pre = from;
			from = next;
		}
		return pre;
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
		Node head = new Node(4);
		head.left = new Node(2);
		head.right = new Node(6);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.right.left = new Node(5);
		head.right.right = new Node(7);
		printTree(head);
		morrisIn(head);
		morrisPre(head);
		morrisPos(head);
		printTree(head);

	}

}
