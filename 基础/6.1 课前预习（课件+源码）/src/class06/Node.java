package class06;

/**
 * 解题模板 节点
 */
import java.util.ArrayList;
import java.util.Arrays;

// 图的存储方式，解题模板，此处运用了邻接表的方式
public class Node {
	public int value;
	public int in; // 入度
	public int out;// 出度
	public ArrayList<Node> nexts; // 邻接节点
	public ArrayList<Edge> edges; // 边

	public Node(int value) {
		this.value = value;
		in = 0;
		out = 0;
		nexts = new ArrayList<>();
		edges = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Node{" +
				"value=" + value +
				", in=" + in +
				", out=" + out +
				"}\n";
	}
}
