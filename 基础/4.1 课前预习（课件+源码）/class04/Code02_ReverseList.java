/**
 * 【题目】 分别实现反转单向链表和反转双向链表的函数
 * 【要求】 如果链表长度为N，时间复杂度要求为O(N)，额外空间复杂度要求为 O(1)
 */
public class Code02_ReverseList {

	// 定义单链表节点
	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	// 单链表的反转
	public static Node reverseList(Node head) {
		Node pre = null;
		Node next = null;
		while (head != null) {
			// 保存当前节点的下一个
			next = head.next;
			// 当前节点下个改为指向上个节点
			head.next = pre;
			// 上个节点的引用下移一位，此刻pre单独一条链表
			pre = head;
			// 当前节点的引用改为next，此刻head单独一条链表
			head = next;
		}
		// pre最后为反转后的链表
		return pre;
	}
	// 定义双链表节点
	public static class DoubleNode {
		public int value;
		public DoubleNode last;
		public DoubleNode next;

		public DoubleNode(int data) {
			this.value = data;
		}
	}

	// 双链表的反转
	public static DoubleNode reverseList(DoubleNode head) {
		DoubleNode pre = null;
		DoubleNode next = null;
		while (head != null) {
			// 保存当前节点的下一个
			next = head.next;
			// 当前节点下个改为指向上个节点,上个节点指向next
			head.next = pre;
			head.last = next;
			pre = head;
			head = next;
		}
		return pre;
	}

	public static void printLinkedList(Node head) {
		System.out.print("Linked List: ");
		while (head != null) {
			System.out.print(head.value + " ");
			head = head.next;
		}
		System.out.println();
	}

	public static void printDoubleLinkedList(DoubleNode head) {
		System.out.print("Double Linked List: ");
		// 预留一个不为空的双向节点
		DoubleNode end = null;
		while (head != null) {
			System.out.print(head.value + " ");
			end = head;
			head = head.next;
		}
		System.out.print("| ");
		while (end != null) {
			System.out.print(end.value + " ");
			end = end.last;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		printLinkedList(head1);
		head1 = reverseList(head1);
		printLinkedList(head1);

		DoubleNode head2 = new DoubleNode(1);
		head2.next = new DoubleNode(2);
		head2.next.last = head2;
		head2.next.next = new DoubleNode(3);
		head2.next.next.last = head2.next;
		head2.next.next.next = new DoubleNode(4);
		head2.next.next.next.last = head2.next.next;
		printDoubleLinkedList(head2);
		printDoubleLinkedList(reverseList(head2));

	}

}
