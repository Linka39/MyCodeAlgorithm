package class07;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 				最大收益
 * 输入：
 * 正数数组costs  		costs[i]表示i号项目的花费
 * 正数数组profits		profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
 * 正数k					k表示你只能串行的最多做k个项目
 * 正数m					m表示你初始的资金
 * 说明：
 * 你每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
 * 输出：
 * 你最后获得的最大钱数。
 */
public class Code05_IPO {
	public static class Node {
		public int p;
		public int c;

		public Node(int p, int c) {
			this.p = p;
			this.c = c;
		}
	}

	public static class MinCostComparator implements Comparator<Node> {
		@Override
		public int compare(Node o1, Node o2) {
			return o1.c - o2.c;
		}
	}


	// 贪心策略，将花费的项目依次进入小顶堆，根据W的范围再进入大顶堆，大顶堆选出最大的
	// W加上收益，再进行一轮小顶堆迁移到大顶堆，直到k次或W没有为止
	public static int findMaximizedCapital(int k, int W, int[] Profits, int[] Capital) {
		Node[] nodes = new Node[Profits.length];
		for (int i = 0; i < Profits.length; i++) {
			nodes[i] = new Node(Profits[i], Capital[i]);
		}

		PriorityQueue<Node> minCostQ = new PriorityQueue<>(new MinCostComparator());
		PriorityQueue<Node> maxProfitQ = new PriorityQueue<>(((o1, o2) -> o2.p - o1.p));
		for (int i = 0; i < nodes.length; i++) {
			minCostQ.add(nodes[i]);
		}
		for (int i = 0; i < k; i++) {
			while (!minCostQ.isEmpty() && minCostQ.peek().c <= W) {
				maxProfitQ.add(minCostQ.poll());
			}
			if (maxProfitQ.isEmpty()) {
				return W;
			}
			W += maxProfitQ.poll().p;
		}
		return W;
	}

	public static void testIPO(){
		int[] costs = new int[]{3, 2, 1, 3, 6};
		int[] profits = new int[]{3, 5, 2, 5, 7};
		System.out.println(findMaximizedCapital(3, 6, costs, profits));
	}
	public static void main(String[] args) {
		testIPO();
	}

}
