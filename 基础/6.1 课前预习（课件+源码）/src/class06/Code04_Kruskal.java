package class06;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;


/**
 * kruskal算法	求最小生成树
 * 适用范围：要求无向图
 */
//undirected graph only
public class Code04_Kruskal {

	// Union-Find Set 并查集
	public static class UnionFind {
		// 父节点Map，记录最终回溯的节点
		private HashMap<Node, Node> fatherMap;
		// 节点所在并查集的数目
		private HashMap<Node, Integer> rankMap;

		public UnionFind() {
			fatherMap = new HashMap<Node, Node>();
			rankMap = new HashMap<Node, Integer>();
		}

		// 获取祖先节点
		private Node findFather(Node n) {
			// 获取该节点的父节点，如果父节点和本节点不同，递归获取祖先节点作为本节点的父节点
			Node father = fatherMap.get(n);
			if (father != n) {
				father = findFather(father);
			}
			fatherMap.put(n, father);
			return father;
		}

		// 初始化并查集
		public void makeSets(Collection<Node> nodes) {
			fatherMap.clear();
			rankMap.clear();
			for (Node node : nodes) {
				fatherMap.put(node, node);
				rankMap.put(node, 1);
			}
		}

		// 判断是否为同一个并查集
		public boolean isSameSet(Node a, Node b) {
			return findFather(a) == findFather(b);
		}

		// 合并 并查集
		public void union(Node a, Node b) {
			if (a == null || b == null) {
				return;
			}
			// 获取两个节点的祖先节点，不同则说明不在一个并查集里
			Node aFather = findFather(a);
			Node bFather = findFather(b);
			if (aFather != bFather) {
				// 获取祖先节点的数目，数目大的作为级别小的父节点(根的覆盖范围更广)，父节点数目更新
				int aFrank = rankMap.get(aFather);
				int bFrank = rankMap.get(bFather);
				if (aFrank <= bFrank) {
					fatherMap.put(aFather, bFather);
					rankMap.put(bFather, aFrank + bFrank);
				} else {
					fatherMap.put(bFather, aFather);
					rankMap.put(aFather, aFrank + bFrank);
				}
			}
		}
	}

	// 定义排序算法
	public static class EdgeComparator implements Comparator<Edge> {

		@Override
		public int compare(Edge o1, Edge o2) {
			return o1.weight - o2.weight;
		}

	}

	public static Set<Edge> kruskalMST(Graph graph) {
		// 初始化并查集和最小堆队列
		UnionFind unionFind = new UnionFind();
		unionFind.makeSets(graph.nodes.values());
		PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
		for (Edge edge : graph.edges) {
			priorityQueue.add(edge);
		}
		Set<Edge> result = new HashSet<>();
		// 最小堆出队，如果两个节点不在同一并查集里，则将边纳入结果里，并查集合并
		while (!priorityQueue.isEmpty()) {
			Edge edge = priorityQueue.poll();
			if (!unionFind.isSameSet(edge.from, edge.to)) {
				result.add(edge);
				unionFind.union(edge.from, edge.to);
			}
		}
		return result;
	}
}
