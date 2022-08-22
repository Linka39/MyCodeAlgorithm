package class06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * 拓扑排序算法
 * 适用范围：要求有向图，且有入度为0的节点，且没有环
 */
public class Code03_TopologySort {

	// directed graph and no loop
	public static List<Node> sortedTopology(Graph graph) {
		HashMap<Node, Integer> inMap = new HashMap<>();
		Queue<Node> zeroInQueue = new LinkedList<>();
		// 定义一个入度为0的节点队列，每次将入度为0的节点放入
		for (Node node : graph.nodes.values()) {
			// 用map来记录节点的入度
			inMap.put(node, node.in);
			if (node.in == 0) {
				zeroInQueue.add(node);
			}
		}
		List<Node> result = new ArrayList<>();
		// 当队列不为空时，进行出队
		while (!zeroInQueue.isEmpty()) {
			// 出队时纳入已读集合
			Node cur = zeroInQueue.poll();
			result.add(cur);
			// 去掉该节点后，遍历后继节点，所有节点入度减1
			for (Node next : cur.nexts) {
				inMap.put(next, inMap.get(next) - 1);
				if (inMap.get(next) == 0) {
					zeroInQueue.add(next);
				}
			}
		}
		return result;
	}
}
