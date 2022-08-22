/**
 * 两个单链表相交的一系列问题
 * 【题目】给定两个可能有环也可能无环的单链表，头节点head1和head2。请实现一个函数，如果两个链表相交，请返回相交的第一个节点。如果不相交，返 回null
 * 【要求】如果两个链表长度之和为N，时间复杂度请达到O(N)，额外空间复杂度 请达到O(1)
 */
public class Code07_FindFirstIntersectNode {

	public static class Node {
		public int value;
		public Node next;

		public Node(int data) {
			this.value = data;
		}
	}

	public static Node getIntersectNode(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node loop1 = getLoopNode(head1);
		Node loop2 = getLoopNode(head2);
		// 如果两条链表相交的话，肯定两条都为环状链表，或都不为环状链表
		if (loop1 == null && loop2 == null) {
			// 都不为环状时执行 noLoop()
			return noLoop(head1, head2);
		}
		if (loop1 != null && loop2 != null) {
			// 都为环状时执行 bothLoop()
			return bothLoop(head1, loop1, head2, loop2);
		}
		return null;
	}

	public static Node getLoopNode(Node head) {
		if (head == null || head.next == null || head.next.next == null) {
			return null;
		}
		// 慢指针一次走一个，快指针一次走两个，满指针先走
		Node n1 = head.next; // n1 -> slow
		Node n2 = head.next.next; // n2 -> fast
		while (n1 != n2) {
			if (n2.next == null || n2.next.next == null) {
				// 有一个返回null，代表不为链环
				return null;
			}
			n2 = n2.next.next;
			n1 = n1.next;
		}
		// 结束循环的话，n1 == n2
		// 此时快指针回到头节点，满指针在相遇的那个节点
		n2 = head; // n2 -> walk again from head
		while (n1 != n2) {
			n1 = n1.next;
			n2 = n2.next;
		}
		// 此时再次相遇时，为环状链表的起始环节点
		return n1;
	}

	public static Node noLoop(Node head1, Node head2) {
		if (head1 == null || head2 == null) {
			return null;
		}
		Node cur1 = head1;
		Node cur2 = head2;
		int n = 0;
		// 如果两个链表都无环的话，这两条链表尾节点会相同，一定会共享尾部
		// 思路：先计算出一条链表的长度，记为n，然后遍历另一条每次下移n--
		// 根据n的正负判断较长的链表
		while (cur1.next != null) {
			n++;
			cur1 = cur1.next;
		}
		while (cur2.next != null) {
			n--;
			cur2 = cur2.next;
		}
		if (cur1 != cur2) {
			return null;
		}
		// 如果(n > 0)，那么第一个链表会更长些
		cur1 = n > 0 ? head1 : head2;
		// 得到较小的链表
		cur2 = cur1 == head1 ? head2 : head1;
		n = Math.abs(n);
		// 获取绝对值，链表1先遍历多余位置
		while (n != 0) {
			n--;
			cur1 = cur1.next;
		}
		// 两条单链表遍历，直到节点地址相同的位置然后返回
		while (cur1 != cur2) {
			cur1 = cur1.next;
			cur2 = cur2.next;
		}
		return cur1;
	}

	public static Node bothLoop(Node head1, Node loop1, Node head2, Node loop2) {
		Node cur1 = null;
		Node cur2 = null;
		// 如果两个环链表节点相同，说明相交的节点在环链表上面
		if (loop1 == loop2) {
			cur1 = head1;
			cur2 = head2;
			int n = 0;
			// 得到链表1到环链的长度
			while (cur1 != loop1) {
				n++;
				cur1 = cur1.next;
			}
			// 得到链表2到环链的长度，在n的基础上相减
			while (cur2 != loop2) {
				n--;
				cur2 = cur2.next;
			}
			// 得到长表cur1，短表cur2
			cur1 = n > 0 ? head1 : head2;
			cur2 = cur1 == head1 ? head2 : head1;
			n = Math.abs(n);
			// 长表减到多余的位置
			while (n != 0) {
				n--;
				cur1 = cur1.next;
			}
			// 两表共同遍历，直到有一个节点相等时，结束遍历
			while (cur1 != cur2) {
				cur1 = cur1.next;
				cur2 = cur2.next;
			}
			return cur1;
		// 如果两个环链表节点不同，说明相交的节点在环表内
		} else {
			// 回到表1的环链起始点，
			cur1 = loop1.next;
			while (cur1 != loop1) {
				if (cur1 == loop2) {
					return loop1;
				}
				cur1 = cur1.next;
			}
			return null;
		}
	}

	public static void main(String[] args) {
		// 1->2->3->4->5->6->7->null
		Node head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);

		// 0->9->8->6->7->null
		Node head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

		// 1->2->3->4->5->6->7->4...
		head1 = new Node(1);
		head1.next = new Node(2);
		head1.next.next = new Node(3);
		head1.next.next.next = new Node(4);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(6);
		head1.next.next.next.next.next.next = new Node(7);
		head1.next.next.next.next.next.next = head1.next.next.next; // 7->4

		// 0->9->8->2...
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next; // 8->2
		System.out.println(getIntersectNode(head1, head2).value);

		// 0->9->8->6->4->5->6..
		head2 = new Node(0);
		head2.next = new Node(9);
		head2.next.next = new Node(8);
		head2.next.next.next = head1.next.next.next.next.next; // 8->6
		System.out.println(getIntersectNode(head1, head2).value);

	}

}
