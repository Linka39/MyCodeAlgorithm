

import java.util.HashMap;

/**
 * 复制含有随机指针节点的链表
 * 【题目】一种特殊的单链表节点类描述如下
 * class Node {
 * 		int value;
 * 		Node next;
 * 		Node rand;
 * 		Node(int val) {
 * 			value = val;
 * 		}
 * 	}
 * 	rand指针是单链表节点结构中新增的指针，rand可能指向链表中的任意一个节点，
 * 	也可能指向null。给定一个由Node节点类型组成的无环单链表的头节点 head，
 * 	请实现一个函数完成这个链表的复制，并返回复制的新链表的头节点。
 * 【要求】时间复杂度O(N)，额外空间复杂度O(1)
 */
public class Code06_CopyListWithRandom {

	public static class Node {
		public int value;
		public Node next;
		public Node rand;

		public Node(int data) {
			this.value = data;
		}
	}

	// 时间O(n)，空间O(n)
	public static Node copyListWithRand1(Node head) {
		HashMap<Node, Node> map = new HashMap<Node, Node>();
		Node cur = head;
		// 将所有节点先转存到HashMap里
		while (cur != null) {
			map.put(cur, new Node(cur.value));
			cur = cur.next;
		}
		// 得到头节点
		cur = head;
		while (cur != null) {
			// map里记录了每个节点的下个指针和随机指针，直到尾节点为空，依次赋值就可以
			map.get(cur).next = map.get(cur.next);
			map.get(cur).rand = map.get(cur.rand);
			cur = cur.next;
		}
		return map.get(head);
	}

	public static Node copyListWithRand2(Node head) {
		if (head == null) {
			return null;
		}
		Node cur = head;
		Node next = null;
		// copy node and link to every node
		// 思路：先遍历节点，生成一条链表，每次遍历，在每个节点中间新建一个节点空间，并赋值下个节点的内容
		// 正常节点与复制节点是相隔的，第二次遍历，并对rand指针进行赋值
		// 第三次遍历拆分链表，复制节点重连正常节点的下个，完成复制链表的重连
		while (cur != null) {
			next = cur.next;
			// 当前节点下个节点新建实例，作为头节点
			cur.next = new Node(cur.value);
			// 将新生成的节点重连上链表
			cur.next.next = next;
			// 当前节点下移
			cur = next;
		}
		// 回到头节点
		cur = head;
		Node curCopy = null;
		// set copy node rand
		while (cur != null) {
			// 保存下下个节点（正常节点）
			next = cur.next.next;
			// 指向下个节点的变量(新生成的节点)进行操作，并对rand指针进行赋值
			curCopy = cur.next;
			curCopy.rand = cur.rand != null ? cur.rand.next : null;
			cur = next;
		}
		Node res = head.next;
		cur = head;
		// split
		while (cur != null) {
			// 正常节点为头节点的下下个
			next = cur.next.next;
			// 复制节点为下个
			curCopy = cur.next;
			cur.next = next;
			// 复制节点重连正常节点的下个，完成复制链表的重构
			curCopy.next = next != null ? next.next : null;
			cur = next;
		}
		return res;
	}

	public static void printRandLinkedList(Node head) {
		Node cur = head;
		System.out.print("order: ");
		while (cur != null) {
			System.out.print(cur.value + " ");
			cur = cur.next;
		}
		System.out.println();
		cur = head;
		System.out.print("rand:  ");
		// 先打印出rand节点，随后指向下个节点
		while (cur != null) {
			System.out.print(cur.rand == null ? "- " : cur.rand.value + " ");
			cur = cur.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head = null;
		Node res1 = null;
		Node res2 = null;
		printRandLinkedList(head);
		res1 = copyListWithRand1(head);
		printRandLinkedList(res1);
		res2 = copyListWithRand2(head);
		printRandLinkedList(res2);
		printRandLinkedList(head);
		System.out.println("=========================");

		head = new Node(1);
		head.next = new Node(2);
		head.next.next = new Node(3);
		head.next.next.next = new Node(4);
		head.next.next.next.next = new Node(5);
		head.next.next.next.next.next = new Node(6);

		head.rand = head.next.next.next.next.next; // 1 -> 6
		head.next.rand = head.next.next.next.next.next; // 2 -> 6
		head.next.next.rand = head.next.next.next.next; // 3 -> 5
		head.next.next.next.rand = head.next.next; // 4 -> 3
		head.next.next.next.next.rand = null; // 5 -> null
		head.next.next.next.next.next.rand = head.next.next.next; // 6 -> 4

		printRandLinkedList(head);
		res1 = copyListWithRand1(head);
		printRandLinkedList(res1);
		res2 = copyListWithRand2(head);
		printRandLinkedList(res2);
		printRandLinkedList(head);
		System.out.println("=========================");

	}

}
