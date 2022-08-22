/**
 * 将单向链表按某值划分成左边小、中间相等、右边大的形式
 * 【题目】给定一个单链表的头节点head，节点的值类型是整型，再给定一个整 数pivot。实现一个调整链表的函数，将链表调整为左部分都是值小于pivot的 节点，中间部分都是值等于pivot的节点，右部分都是值大于pivot的节点。
 * 【进阶】在实现原问题功能的基础上增加如下的要求
 * 【要求】调整后所有小于pivot的节点之间的相对顺序和调整前一样
 * 【要求】调整后所有等于pivot的节点之间的相对顺序和调整前一样
 * 【要求】调整后所有大于pivot的节点之间的相对顺序和调整前一样
 * 【要求】时间复杂度请达到O(N)，额外空间复杂度请达到O(1)。
 */
public class Code05_SmallerEqualBigger {

	public static class Node {
		public int value;
		public Node next;
		public Node(int data) {
			this.value = data;
		}
	}

	public static Node listPartition1(Node head, int pivot) {
		if (head == null) {
			return head;
		}
		Node cur = head;
		int i = 0;
		// 对节点进行遍历，获取链表的长度
		while (cur != null) {
			i++;
			cur = cur.next;
		}
		// 新建一个关于此链表的数组，转存链表
		Node[] nodeArr = new Node[i];
		i = 0;
		cur = head;
		// 将每个节点都放到相应的数组上
		for (i = 0; i != nodeArr.length; i++) {
			nodeArr[i] = cur;
			cur = cur.next;
		}
		// 执行快速排序的子函数
		arrPartition(nodeArr, pivot);
		for (i = 1; i != nodeArr.length; i++) {
			// 让数组的前一个节点指向下一个
			nodeArr[i - 1].next = nodeArr[i];
		}
		nodeArr[i - 1].next = null;
		return nodeArr[0];
	}

	public static void arrPartition(Node[] nodeArr, int pivot) {
		int small = -1;
		int big = nodeArr.length;
		int index = 0;
		while (index != big) {
			if (nodeArr[index].value < pivot) {
				swap(nodeArr, ++small, index++);
			} else if (nodeArr[index].value == pivot) {
				index++;
			} else {
				swap(nodeArr, --big, index);
			}
		}
	}

	public static void swap(Node[] nodeArr, int a, int b) {
		Node tmp = nodeArr[a];
		nodeArr[a] = nodeArr[b];
		nodeArr[b] = tmp;
	}

	public static Node listPartition2(Node head, int pivot) {
		Node sH = null; // small head
		Node sT = null; // small tail
		Node eH = null; // equal head
		Node eT = null; // equal tail
		Node bH = null; // big head
		Node bT = null; // big tail
		Node next = null; // save next node
		// every node distributed to three lists
		// 将链表进行重构
		while (head != null) {
			// 保存当前节点的下个位置
			next = head.next;
			head.next = null;
			// 当前节点小于基准值时
			if (head.value < pivot) {
				// 最小链表为空时，构造链表
				if (sH == null) {
					sH = head;
					sT = head;
				// 最小链表不为空时，因为小于基准值，采取头插法插入
				} else {
					sT.next = head;
					sT = head;
				}
			// 当前节点等于基准值时
			} else if (head.value == pivot) {
				// 中间链表为空时，构造链表
				if (eH == null) {
					eH = head;
					eT = head;
				// 中间链表不为空时，等于基准值，采取头插法插入
				} else {
					eT.next = head;
					eT = head;
				}
			} else {
				// 最大链表为空时，构造链表
				if (bH == null) {
					bH = head;
					bT = head;
				// 最大链表不为空时，大于基准值，采取尾插法插入
				} else {
					bT.next = head;
					bT = head;
				}
			}
			head = next;
		}
		// small and equal reconnect  小链表尾部连接等于链表
		if (sT != null) {
			sT.next = eH;
			eT = eT == null ? sT : eT;
		}
		// all reconnect 重连所有
		if (eT != null) {
			eT.next = bH;
		}
		if (bT != null){
			bT.next = null;
		}
		return sH != null ? sH :
				eH != null ? eH : bH;
	}

	public static void printLinkedList(Node node) {
		System.out.print("Linked List: ");
		while (node != null) {
			System.out.print(node.value + " ");
			node = node.next;
		}
		System.out.println();
	}

	public static void main(String[] args) {
		Node head1 = new Node(7);
		head1.next = new Node(9);
		head1.next.next = new Node(1);
		head1.next.next.next = new Node(8);
		head1.next.next.next.next = new Node(5);
		head1.next.next.next.next.next = new Node(2);
		head1.next.next.next.next.next.next = new Node(5);
		printLinkedList(head1);
		// head1 = listPartition1(head1, 4);
		head1 = listPartition2(head1, 5);
		printLinkedList(head1);

	}

}
