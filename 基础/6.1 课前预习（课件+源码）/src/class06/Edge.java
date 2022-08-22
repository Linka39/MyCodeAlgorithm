package class06;
/**
 * 解题模板 边
 */
public class Edge {
	public int weight;	// 边的权重
	public Node from;	// 边的入节点
	public Node to;		// 边的出节点

	public Edge(int weight, Node from, Node to) {
		this.weight = weight;
		this.from = from;
		this.to = to;
	}

	@Override
	public String toString() {
		return "Edge{" +
				"weight=" + weight +
				", from=" + from +
				", to=" + to +
				'}';
	}
}
