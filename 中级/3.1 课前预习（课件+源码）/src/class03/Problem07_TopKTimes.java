package class03;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.PriorityQueue;

/**
 * 给定一个字符串类型的数组arr，求其中出现次数最多的前K个
 */
public class Problem07_TopKTimes {

	// 思路：构造一个带节点和出现次数的结构，将数组放入到map里，然后循环map，构造node节点
	// 将节点依次放入PriorityQueue里，放topK个到里面构造一个小顶堆，堆满后当前大于堆顶值后入堆，保证堆里为 topK 个最大值
	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	// 比较器实现
	public static class NodeComparator implements Comparator<Node> {

		@Override
		public int compare(Node o1, Node o2) {
			return o1.times - o2.times;
		}

	}

	public static void printTopKAndRank(String[] arr, int topK) {
		if (arr == null || arr.length == 0 || topK < 1) {
			return;
		}
		// 利用hashmap来创建节点实例
		HashMap<String, Integer> map = new HashMap<>();
		for (String str : arr) {
			if (!map.containsKey(str)) {
				map.put(str, 0);
			}
			map.put(str, map.get(str) + 1);
		}
		topK = Math.min(arr.length, topK);
		PriorityQueue<Node> heap = new PriorityQueue<>(new NodeComparator());
		for (Entry<String, Integer> entry : map.entrySet()) {
			// 创建新的节点
			Node cur = new Node(entry.getKey(), entry.getValue());
			// 将topK个节点放入小顶堆中，如果堆中满的话进行出堆
			if (heap.size() < topK) {
				heap.add(cur);
			} else {
				// 保证堆里为 topK 个最大值
				if (heap.peek().times < cur.times) {
					heap.poll();
					heap.add(cur);
				}
			}

		}
		while (!heap.isEmpty()) {
			System.out.println(heap.poll().str);
		}
	}

	public static String[] generateRandomArray(int len, int max) {
		String[] res = new String[len];
		for (int i = 0; i != len; i++) {
			res[i] = String.valueOf((int) (Math.random() * (max + 1)));
		}
		return res;
	}

	public static void printArray(String[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		String[] arr1 = { "A", "B", "A", "C", "A", "C", "B", "B", "B", "K" };
		printTopKAndRank(arr1, 2);

		String[] arr2 = generateRandomArray(50, 10);
		int topK = 3;
		printArray(arr2);
		printTopKAndRank(arr2, topK);

	}
}
