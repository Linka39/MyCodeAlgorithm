package class06;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 双向链表节点结构和二叉树节点结构是一样的，如果你把last认为是left， next认为是next的话。
 * 给定一个搜索二叉树的头节点head，请转化成一条有序的双向链表，并返回链 表的头节点。
 */
public class Problem02_BSTtoDoubleLinkedList {

	public static class Node {
		public int value;
		public Node left;
		public Node right;

		public Node(int data) {
			this.value = data;
		}
	}

	// 思路1：新建一个链表，将中序遍历的结果放入链表中。
	public static Node convert1(Node head) {
		Queue<Node> queue = new LinkedList<Node>();
		inOrderToQueue(head, queue);
		if (queue.isEmpty()) {
			return head;
		}
		// 将链表中的结果第一项作为头节点，依次处理节点的左右指针，直到链表为空，处理完毕
		head = queue.poll();
		Node pre = head;
		pre.left = null;
		Node cur = null;
		while (!queue.isEmpty()) {
			cur = queue.poll();
			pre.right = cur;
			cur.left = pre;
			pre = cur;
		}
		pre.right = null;
		return head;
	}

	public static void inOrderToQueue(Node head, Queue<Node> queue) {
		if (head == null) {
			return;
		}
		inOrderToQueue(head.left, queue);
		queue.offer(head);
		inOrderToQueue(head.right, queue);
	}

	// 思路2：树形dp思想，构造一个结果集，包含一个起始和结束的节点位置
	public static class RetrunType {
		public Node start;
		public Node end;

		public RetrunType(Node start, Node end) {
			this.start = start;
			this.end = end;
		}
	}

	public static Node convert2(Node head) {
		if (head == null) {
			return null;
		}
		return process(head).start;
	}

	// 从局部开始，每个节点都只关注自己左子树形成的结果集，将其结束节点接入当前节点，并连入右孩子结果集的开始节点。
	public static RetrunType process(Node head) {
		// base 情况
		if (head == null) {
			return new RetrunType(null, null);
		}
		RetrunType leftList = process(head.left);
		RetrunType rightList = process(head.right);
		// 当节点不为空的进行相连
		if (leftList.end != null) {
			leftList.end.right = head;
		}
		head.left = leftList.end;
		head.right = rightList.start;
		if (rightList.start != null) {
			rightList.start.left = head;
		}
		// 最终返回起始和结束节点的结果集
		return new RetrunType(leftList.start != null ? leftList.start : head,
				rightList.end != null ? rightList.end : head);
	}

	public static void printBSTInOrder(Node head) {
		System.out.print("BST in-order: ");
		if (head != null) {
			inOrderPrint(head);
		}
		System.out.println();
	}

	public static void inOrderPrint(Node head) {
		if (head == null) {
			return;
		}
		inOrderPrint(head.left);
		System.out.print(head.value + " ");
		inOrderPrint(head.right);
	}

	public static void printDoubleLinkedList(Node head) {
		System.out.print("Double Linked List: ");
		Node end = null;
		while (head != null) {
			System.out.print(head.value + " ");
			end = head;
			head = head.right;
		}
		System.out.print("| ");
		while (end != null) {
			System.out.print(end.value + " ");
			end = end.left;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = new Node(5);
		head.left = new Node(2);
		head.right = new Node(9);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.left.right.right = new Node(4);
		head.right.left = new Node(7);
		head.right.right = new Node(10);
		head.left.left = new Node(1);
		head.right.left.left = new Node(6);
		head.right.left.right = new Node(8);

		printBSTInOrder(head);
		head = convert1(head);
		printDoubleLinkedList(head);

		head = new Node(5);
		head.left = new Node(2);
		head.right = new Node(9);
		head.left.left = new Node(1);
		head.left.right = new Node(3);
		head.left.right.right = new Node(4);
		head.right.left = new Node(7);
		head.right.right = new Node(10);
		head.left.left = new Node(1);
		head.right.left.left = new Node(6);
		head.right.left.right = new Node(8);

		printBSTInOrder(head);
		head = convert2(head);
		printDoubleLinkedList(head);

	}

}
