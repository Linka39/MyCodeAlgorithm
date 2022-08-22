package class07;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * 一块金条切成两半，是需要花费和长度数值一样的铜板的。比如长度为20的金条，不管切成长度多大的两半，都要花费20个铜板。
 *
 * 一群人想整分整块金条，怎么分最省铜板? 例如,给定数组{10,20,30}，代表一共三个人，整块金条长度为10+20+30=60。
 * 金条要分成10,20,30三个部分。如果先把长度60的金条分成10和50，花费60； 再把长度50的金条分成20和30，花费50；
 * 一共花费110铜板。 但是如果先把长度60的金条分成30和30，花费60；再把长度30金条分成10和20， 花费30；
 * 一共花费90铜板。 输入一个数组，返回分割的最小代价。
 *
 * 采取哈夫曼树的方式。
 */
public class Code03_LessMoneySplitGold {

	// 将数组里的数据都放入队列，每次取最小的两个值
	public static int lessMoney(int[] arr) {
		PriorityQueue<Integer> pQ = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++) {
			pQ.add(arr[i]);
		}
		int sum = 0;
		int cur = 0;
		// 将两个值相加后，依次放入队列里，排序后进行下一轮添加
		while (pQ.size() > 1) {
			cur = pQ.poll() + pQ.poll();
			sum += cur;
			pQ.add(cur);
		}
		return sum;
	}

	public static class MinheapComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o1 - o2; // < 0  o1 < o2  负数
		}

	}

	public static class MaxheapComparator implements Comparator<Integer> {

		@Override
		public int compare(Integer o1, Integer o2) {
			return o2 - o1; // <   o2 < o1
		}

	}

	public static void main(String[] args) {
		// solution
		int[] arr = { 6, 7, 8, 9 };
		System.out.println(lessMoney(arr));

		// 哈夫曼树测试
		getHafuMan();

		int[] arrForHeap = { 3, 5, 2, 7, 0, 1, 6, 4 };

		// min heap  下面为堆的测试代码，PriorityQueue默认小顶堆
		PriorityQueue<Integer> minQ1 = new PriorityQueue<>();
		for (int i = 0; i < arr.length; i++) {
			minQ1.add(arr[i]);
		}
		while (!minQ1.isEmpty()) {
			System.out.print(minQ1.poll() + " ");
		}
		System.out.println();

		// min heap use Comparator
		PriorityQueue<Integer> minQ2 = new PriorityQueue<>(new MinheapComparator());
		for (int i = 0; i < arrForHeap.length; i++) {
			minQ2.add(arrForHeap[i]);
		}
		while (!minQ2.isEmpty()) {
			System.out.print(minQ2.poll() + " ");
		}
		System.out.println();

		// max heap use Comparator
		PriorityQueue<Integer> maxQ = new PriorityQueue<>(new MaxheapComparator());
		for (int i = 0; i < arrForHeap.length; i++) {
			maxQ.add(arrForHeap[i]);
		}
		while (!maxQ.isEmpty()) {
			System.out.print(maxQ.poll() + " ");
		}

	}


	// 定义哈夫曼树的结构
	public static class HafuManNode {
		String data;
		int weight;
		HafuManNode left;
		HafuManNode right;

		public HafuManNode(String data, int weight) {
			this.data = data;
			this.weight = weight;
			left = null;
			right = null;
		}

		@Override
		public String toString() {
			return "HafuManNode{" +
					"data='" + data + '\'' +
					", weight=" + weight +
					", left=" + left +
					", right=" + right +
					'}';
		}
	}

	// 初始化节点数组，并按照weight进行升序排序
	public static PriorityQueue initList() {
		HafuManNode[] nodeArr = new HafuManNode[]{
				new HafuManNode("a", 6),
				new HafuManNode("b", 7),
				new HafuManNode("c", 8),
				new HafuManNode("d", 9)
		};
		PriorityQueue<HafuManNode> nodeQueue = new PriorityQueue<>(((o1, o2) -> o1.weight - o2.weight));
		for (HafuManNode hafuManNode : nodeArr) {
			nodeQueue.add(hafuManNode);
		}
		return nodeQueue;
	}

	// 进行哈夫曼二叉树的构建
	public static void getHafuMan() {
		// 初始化数据，并按照权重进行升序排序
		PriorityQueue<HafuManNode> nodeQueue = initList();
		HafuManNode head = null;
		// 对排序好的节点数组，利用贪心法构建，最后返回根结点
		while (nodeQueue.size() > 1) {
			HafuManNode left = nodeQueue.poll();
			HafuManNode right = nodeQueue.poll();
			head = new HafuManNode(null, left.weight + right.weight);
			head.left = left;
			head.right = right;
			nodeQueue.offer(head);
		}
		printHafuMan(head, "");
	}

	// 打印哈夫曼编码
	public static void printHafuMan(HafuManNode head, String hafumanCode) {
		// data为null的话，代表叶子节点，可以进行操作并打破循环
		if (head.data != null) {
			System.out.println("数据：" + head.data + ", 编码：" + hafumanCode);
			return;
		}
		// data不为null的话进行打印，左孩子1，右孩子0
		printHafuMan(head.left, hafumanCode + "1");
		printHafuMan(head.right, hafumanCode + "0");
	}

}
