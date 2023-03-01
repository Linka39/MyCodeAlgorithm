package class05;

import java.util.HashMap;

/**
 * 设计并实现TopKRecord结构，可以不断地向其中加入字符串，并且可以根据字 符串出现的情况随时打印加入次数最多的前k个字符串。
 * 具体为:
 * 1）k在TopKRecord实例生成时指定，并且不再变化(k是构造TopKRecord的参数)。
 * 2）含有 add(String str)方法，即向TopKRecord中加入字符串。
 * 3）含有 printTopK()方法，即打印加入次数最多的前k个字符串，打印有哪些 字符串和对应的次数即可，不要求严格按排名顺序打印。
 * 4）如果在出现次数最多的前k个字符串中，最后一名的字符串有多个，比如出 现次数最多的前3个字符串具体排名为：
 * A 100次  B 90次  C 80次  D 80次  E 80次，
 * 其他任何字符串出现次数都 不超过80次   那么只需要打印3个，打印ABC、ABD、ABE都可以。
 * 也就是说可以随意抛弃最 后一名，只要求打印k个
 *
 * 要求：
 * 1）在任何时候，add 方法的时间复杂度不超过 O(logk)
 * 2）在任何时候，printTopK方法的时间复杂度不超过O(k)。
 */
public class Problem05_TopKTimesRealTime {

	public static class Node {
		public String str;
		public int times;

		public Node(String s, int t) {
			str = s;
			times = t;
		}
	}

	// 思路：需要一个自定义的小顶堆，或者参考 Problem07_TopKTimes，使用 prorityQueue
	public static class TopKRecord {
		private Node[] heap;
		private int index;
		private HashMap<String, Node> strNodeMap;
		private HashMap<Node, Integer> nodeIndexMap;

		// 初始化当前堆
		public TopKRecord(int size) {
			heap = new Node[size];
			index = 0;
			strNodeMap = new HashMap<String, Node>();
			nodeIndexMap = new HashMap<Node, Integer>(); // 记录节点在当前堆中的索引位置，-1 则代表在堆外
		}

		public void add(String str) {
			Node curNode = null;
			int preIndex = -1;
			if (!strNodeMap.containsKey(str)) {
				curNode = new Node(str, 1);
				strNodeMap.put(str, curNode);
				nodeIndexMap.put(curNode, -1);
			} else {
				curNode = strNodeMap.get(str);
				curNode.times++;
				preIndex = nodeIndexMap.get(curNode);
			}
			if (preIndex == -1) {
				// 如果当前堆已满，堆顶元素换成当前节点进行 headfy, 维护堆的索引矩阵
				if (index == heap.length) {
					if (heap[0].times < curNode.times) {
						nodeIndexMap.put(heap[0], -1);
						nodeIndexMap.put(curNode, 0);
						heap[0] = curNode;
						heapify(0, index);
					}
				} else {
					// 不满的话执行 heapInsert
					nodeIndexMap.put(curNode, index);
					heap[index] = curNode;
					heapInsert(index++);
				}
			} else {
				heapify(preIndex, index);
			}
		}

		public void printTopK() {
			System.out.println("TOP: ");
			for (int i = 0; i != heap.length; i++) {
				if (heap[i] == null) {
					break;
				}
				System.out.print("Str: " + heap[i].str);
				System.out.println(" Times: " + heap[i].times);
			}
		}

		private void heapInsert(int index) {
			while (index != 0) {
				int parent = (index - 1) / 2;
				if (heap[index].times < heap[parent].times) {
					swap(parent, index);
					index = parent;
				} else {
					break;
				}
			}
		}

		private void heapify(int index, int heapSize) {
			int l = index * 2 + 1;
			int r = index * 2 + 2;
			int smallest = index;
			while (l < heapSize) {
				if (heap[l].times < heap[index].times) {
					smallest = l;
				}
				if (r < heapSize && heap[r].times < heap[smallest].times) {
					smallest = r;
				}
				if (smallest != index) {
					swap(smallest, index);
				} else {
					break;
				}
				index = smallest;
				l = index * 2 + 1;
				r = index * 2 + 2;
			}
		}

		private void swap(int index1, int index2) {
			nodeIndexMap.put(heap[index1], index2);
			nodeIndexMap.put(heap[index2], index1);
			Node tmp = heap[index1];
			heap[index1] = heap[index2];
			heap[index2] = tmp;
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
		TopKRecord record = new TopKRecord(2);
		record.add("zuo");
		record.printTopK();
		record.add("cheng");
		record.add("cheng");
		record.printTopK();
		record.add("Yun");
		record.add("Yun");
		record.printTopK();

	}
}
