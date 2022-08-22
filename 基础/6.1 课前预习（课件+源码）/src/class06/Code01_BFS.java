package class06;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 图的宽度优先遍历
 * 1，利用队列实现
 * 2，从源节点开始依次按照宽度进队列，然后弹出
 * 3，每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
 * 4，直到队列变空
 */
public class Code01_BFS {

	public static void bfs(Node node) {
		if (node == null) {
			return;
		}
		// 进行宽度入队
		Queue<Node> queue = new LinkedList<>();
		// 判断重复的set
		HashSet<Node> map = new HashSet<>();
		queue.add(node);
		map.add(node);
		while (!queue.isEmpty()) {
			Node cur = queue.poll();
			// 在出队时，可以消费节点
			System.out.println(cur.value);
			for (Node next : cur.nexts) {
				// 每弹出一个点，把该节点所有没有进过队列的邻接点放入队列
				if (!map.contains(next)) {
					map.add(next);
					queue.add(next);
				}
			}
		}
	}

}
