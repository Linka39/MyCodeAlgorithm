package class06;

import java.util.HashSet;
import java.util.Stack;

/**
 * 广度优先遍历
 * 1，利用栈实现
 * 2，从源节点开始把节点按照深度放入栈，然后弹出
 * 3，每弹出一个点，把该节点下一个没有进过栈的邻接点放入栈
 * 4，直到栈变空
 */
public class Code02_DFS {

	public static void dfs(Node node) {
		if (node == null) {
			return;
		}
		// 进行深度入栈
		Stack<Node> stack = new Stack<>();
		// 判断重复的set
		HashSet<Node> set = new HashSet<>();
		// 在进栈时，消费节点，并纳入已消费集合
		stack.add(node);
		set.add(node);
		System.out.println(node.value);
		while (!stack.isEmpty()) {
			Node cur = stack.pop();
			// 每弹出一个点，循环改节点的下节点，把该节点下一个没有进过栈的邻接点放入栈
			for (Node next : cur.nexts) {
				if (!set.contains(next)) {
					stack.push(cur);
					stack.push(next);
					set.add(next);
					System.out.println(next.value);
					break;
				}
			}
		}
	}

}
