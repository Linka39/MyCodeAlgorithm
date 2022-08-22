package class06;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * prim算法  最小生成树
 * 适用范围：要求无向图
 */
// undirected graph only
public class Code05_Prim {

	public static class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}

	}

	// 返回最小边的结果集
	public static Set<Edge> primMST(Graph graph) {
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(
				new EdgeComparator());
		HashSet<Node> set = new HashSet<>();
		Set<Edge> result = new HashSet<>();
		// 遍历图里的每个点，将每个点对应的边放入最小堆，方便回溯
		for (Node node : graph.nodes.values()) {
			if (!set.contains(node)) {
				// 已访问的话放入set里，以此来过滤
				set.add(node);
				for (Edge edge : node.edges) {
					priorityQueue.add(edge);
				}
				// 最小堆出队
				while (!priorityQueue.isEmpty()) {
					Edge edge = priorityQueue.poll();
					Node toNode = edge.to;
					// 出队后的点判断有没有被访问过，没有的话纳入结果集，并把对应的边放入队列
					if (!set.contains(toNode)) {
						set.add(toNode);
						result.add(edge);
						for (Edge nextEdge : toNode.edges) {
							priorityQueue.add(nextEdge);
						}
					}
				}
			}
		}
		return result;
	}

	public static HashSet<Edge> primDrected(Graph graph) {
		HashSet<Edge> edges = new HashSet<>();
		HashSet<Node> visited = new HashSet<>();

		PriorityQueue<Edge> edgesQueue = new PriorityQueue<>(new EdgeComparator());
		Node from = graph.nodes.get(0);
		visited.add(from);
		for (Edge edge : from.edges) {
			edgesQueue.offer(edge);
		}
		Edge temp = null;
		Node to = null;
		while (!edgesQueue.isEmpty()) {
			temp = edgesQueue.poll();
			to = temp.to;
			if (!visited.contains(to)) {
				edges.add(temp);
				visited.add(to);
				System.out.print(temp.weight + "  ");

				for (Edge edge : to.edges) {
					if (!edges.contains(edge))
						edgesQueue.offer(edge);
				}
			}
		}
		System.out.println();
		return edges;
	}

	// 请保证graph是连通图
	// graph[i][j]表示点i到点j的距离，如果是系统最大值代表无路
	// 返回值是最小连通图的路径之和
	public static int prim(int[][] graph) {
		int size = graph.length;
		int[] distances = new int[size];
		boolean[] visit = new boolean[size];
		visit[0] = true;
		for (int i = 0; i < size; i++) {
			distances[i] = graph[0][i];
		}
		int sum = 0;
		for (int i = 1; i < size; i++) {
			int minPath = Integer.MAX_VALUE;
			int minIndex = -1;
			for (int j = 0; j < size; j++) {
				if (!visit[j] && distances[j] < minPath) {
					minPath = distances[j];
					minIndex = j;
				}
			}
			if (minIndex == -1) {
				return sum;
			}
			visit[minIndex] = true;
			sum += minPath;
			for (int j = 0; j < size; j++) {
				if (!visit[j] && distances[j] > graph[minIndex][j]) {
					distances[j] = graph[minIndex][j];
				}
			}
		}
		return sum;
	}

	public static void main(String[] args) {
		System.out.println("hello world!");
	}

}
