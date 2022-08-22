package class06;

import java.util.Arrays;
import java.util.Optional;


/**
 *
 * 根据邻接矩阵生成 图的解题模板
 */
public class GraphGenerator {

	public static Graph createGraph(Integer[][] matrix) {
		Graph graph = new Graph();
		for (int i = 0; i < matrix.length; i++) {
			Integer weight = matrix[i][0];
			Integer from = matrix[i][1];
			Integer to = matrix[i][2];
			if (!graph.nodes.containsKey(from)) {
				graph.nodes.put(from, new Node(from));
			}
			if (!graph.nodes.containsKey(to)) {
				graph.nodes.put(to, new Node(to));
			}
			Node fromNode = graph.nodes.get(from);
			Node toNode = graph.nodes.get(to);
			Edge newEdge = new Edge(weight, fromNode, toNode);
			fromNode.nexts.add(toNode);
			fromNode.out++;
			toNode.in++;
			fromNode.edges.add(newEdge);
			graph.edges.add(newEdge);
		}
		return graph;
	}

	public static void main(String[] args) {
		Graph myGraph = getDirectGraph();
		System.out.println(myGraph.toString());
	}


	/**
	 *    有向表
	 *  							1	2	3	4	5	6	7
	 *  			  ↗  5		1[	0	1	0	0	0	0	0 ]
	 *  		↗  3	↙		2[	0	0	1	1	0	0	0 ]
	 *  1 → 2      ↑  ↙			3[	0	0	0	0	1	0	0 ]
	 * 			↘  4  →  6		4[	0	0	1	0	0	1	0 ]
	 *  				 ↓		5[	0	0	0	1	0	0	0 ]
	 * 					 7		6[	0	0	0	0	0	0	1 ]
	 * 					 		7[	0	0	0	0	0	0	0 ]
	 *
	 */
	public static Graph getDirectGraph() {
		int[] Xnodes = new int[10];
		int[][] Graph = new int[10][];
		Arrays.fill(Xnodes, 0);
		Graph[0] = new int[]{0, 1, 0, 0, 0, 0, 0};
		Graph[1] = new int[]{0, 0, 1, 1, 0, 0, 0};
		Graph[2] = new int[]{0, 0, 0, 0, 1, 0, 0};
		Graph[3] = new int[]{0, 0, 1, 0, 0, 1, 0};
//		Graph[4] = new int[]{0, 0, 0, 0, 0, 0, 0}; // 去除环
		Graph[4] = new int[]{0, 0, 0, 1, 0, 0, 0};
		Graph[5] = new int[]{0, 0, 0, 0, 0, 0, 1};
		Graph[6] = new int[]{0, 0, 0, 0, 0, 0, 0};

		return fromMatrix(Graph, 7);
	}

	/**
	 *    无向表
	 *  							1	2	3	4	5	6	7
	 *  			  ↗  5		1[	0	1	0	0	0	0	0 ]
	 *  		↗  3	↙		2[	1	0	1	1	0	0	0 ]
	 *  1 → 2      ↑  ↙			3[	0	1	0	1	1	0	0 ]
	 * 			↘  4  →  6		4[	0	1	1	0	0	1	0 ]
	 *  				 ↓		5[	0	0	1	1	0	0	0 ]
	 * 					 7		6[	0	0	0	1	0	0	1 ]
	 *							7[	0	0	0	0	0	1	0 ]
	 */
	public static Graph getNoDirectGraph() {
		int[] Xnodes = new int[10];
		int[][] Graph = new int[10][];
		Arrays.fill(Xnodes, 0);
		Graph[0] = new int[]{0, 1, 0, 0, 0, 0, 0};
		Graph[1] = new int[]{1, 0, 1, 1, 0, 0, 0};
		Graph[2] = new int[]{0, 1, 0, 1, 1, 0, 0};
		Graph[3] = new int[]{0, 1, 1, 0, 0, 1, 0};
		Graph[4] = new int[]{0, 0, 1, 1, 0, 0, 0};
		Graph[5] = new int[]{0, 0, 0, 1, 0, 0, 1};
		Graph[6] = new int[]{0, 0, 0, 0, 0, 1, 0};

		return fromMatrix(Graph, 7);
	}

	public static Graph fromMatrix(int[][] matrix, int nodeSize) {
		Graph graph = new Graph();
		for (int i = 0; i < nodeSize; i++) {
			graph.nodes.put(i, new Node(i + 1));
		}
		nodeSize = nodeSize == 0 ? 7 : nodeSize;
		for (int i = 0; i < nodeSize; i++) {
			int[] Xnodes = matrix[i];
			for (int j = 0; j < nodeSize; j++) {
				if (Xnodes[j] != 0) {
					// 处理节点
					Node from = graph.nodes.get(i);
					Node to = graph.nodes.get(j);
					from.out++; // from节点的出度 +1
					to.in++; // to节点的入度+1
					from.nexts.add(to);  // from节点的下节点塞入

					// 处理边
					Edge edge = new Edge(Xnodes[j], from, to);  // 新建一条边
					graph.edges.add(edge);  // 将边加入图里
					from.edges.add(edge);   // 将边加入所在节点
					to.edges.add(edge);
				}
			}
		}
		return graph;
	}

}

