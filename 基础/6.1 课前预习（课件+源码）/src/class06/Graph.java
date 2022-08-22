package class06;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 图的类
 */
public class Graph {
	public HashMap<Integer,Node> nodes;	// 邻接表的链表，图的点
	public HashSet<Edge> edges;			// 图的边

	public Graph() {
		nodes = new HashMap<>();
		edges = new HashSet<>();
	}

	@Override
	public String toString() {
		return "Graph{\n" +
				"nodes=" + nodes +
				", \nedges=" + edges +
				'}';
	}
}
