package class08;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * 给定两个字符串，记为start和to，再给定一个字符串列表 list，list 中一定包含to list中没有重复字符串，所有的字符串都是小写的。
 * 规定：
 * start每次只能改变一个字符，最终的目标是彻底变成to，但是每次变成的新字符串必须在 list 中存在。 请返回所有最短的变换路径。
 * 【举例】
 * start="abc",end="cab",list={"cab","acc","cbc","ccc","acb","cac","cbb","aab","abb"}
 * 转换路径的方法有很多种，但所有最短的转换路径如下:
 * abc -> abb -> aab -> cab
 * abc -> abb -> cbb -> cab
 * abc -> cbc -> cac -> cab
 * abc -> cbc -> cbb -> cab
 */
public class Problem07_WordMinPaths {

	// 思路：图的最短路径，首先得到一个以每个字符串为 key ，可相连的邻接矩阵 nexts。然后宽度优先遍历，得到一个以 start 作为起点的，以 key 作为终点的
	// 距离数组。然后递归查距离刚好为 distances+1 的节点，将路径上的节点放到 list 里，从而返回最近的 list 数组
	// 返回一个以 list 作为值的数组
	public static List<List<String>> findMinPaths(String start, String to,
			List<String> list) {
		list.add(start);
		// 返回一个邻接表
		HashMap<String, ArrayList<String>> nexts = getNexts(list);
		// 得到每个 key 的宽度
		HashMap<String, Integer> distances = getDistances(start, nexts);
		LinkedList<String> pathList = new LinkedList<>();
		List<List<String>> res = new ArrayList<>();
		// 得到每个从 start 到 to 的最小路线
		getShortestPaths(start, to, nexts, distances, pathList, res);
		return res;
	}

	// 存入每个字符串可对应的链表，形成一个邻接链表，链表中每个字符中与 key 都相差一个字符
	public static HashMap<String, ArrayList<String>> getNexts(List<String> words) {
		Set<String> dict = new HashSet<>(words);
		HashMap<String, ArrayList<String>> nexts = new HashMap<>();
		// 初始化邻接链表
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), new ArrayList<>());
		}
		// 得到以每一个 word 作为起点的链表
		for (int i = 0; i < words.size(); i++) {
			nexts.put(words.get(i), getNext(words.get(i), dict));
		}
		return nexts;
	}

	// 返回一个只改变 word 中的一个字符，并且在 dict 中存在的字符，最终返回以 word 为key的链表
	private static ArrayList<String> getNext(String word, Set<String> dict) {
		ArrayList<String> res = new ArrayList<String>();
		char[] chs = word.toCharArray();
		for (char cur = 'a'; cur <= 'z'; cur++) {
			// 与 word 中字符有不同的话，看此字符在 dict 中是否存在，存在的话添加到路径里
			for (int i = 0; i < chs.length; i++) {
				if (chs[i] != cur) {
					char tmp = chs[i];
					chs[i] = cur;
					if (dict.contains(String.valueOf(chs))) {
						res.add(String.valueOf(chs));
					}
					chs[i] = tmp;
				}
			}
		}
		return res;
	}

	// 类似图的广度优先遍历，distances 中记录每个 key 离起始节点 start 的距离
	public static HashMap<String, Integer> getDistances(String start,
			HashMap<String, ArrayList<String>> nexts) {
		HashMap<String, Integer> distances = new HashMap<>();
		distances.put(start, 0);
		Queue<String> queue = new LinkedList<String>();
		queue.add(start);
		HashSet<String> set = new HashSet<String>(); // 以访问的点
		set.add(start);
		while (!queue.isEmpty()) {
			String cur = queue.poll();
			for (String str : nexts.get(cur)) {
				// 没访问过这个 key 的话，进行计算，distances 距离加1
				if (!set.contains(str)) {
					distances.put(str, distances.get(cur) + 1);
					queue.add(str);
					set.add(str);
				}
			}
		}
		return distances;
	}

	private static void getShortestPaths(String cur, String to,
			HashMap<String, ArrayList<String>> nexts,
			HashMap<String, Integer> distances, LinkedList<String> solution,
			List<List<String>> res) {
		// solution 中放入遍历的路线
		solution.add(cur);
		// 等于结束节点时，纳入结果集中
		if (to.equals(cur)) {
			res.add(new LinkedList<String>(solution));
		} else {
			for (String next : nexts.get(cur)) {
				// 下一个节点的距离正好等于当前位置 +1 时，将将节点放入递归里
				if (distances.get(next) == distances.get(cur) + 1) {
					getShortestPaths(next, to, nexts, distances, solution, res);
				}
			}
		}
		// 递归回调时还原现场
		solution.pollLast();
	}

	public static void main(String[] args) {
		String start = "abc";
		String end = "cab";
		String[] test = { "abc", "cab", "acc", "cbc", "ccc", "acb","cac", "cbb",
				"aab", "abb" };
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i < test.length; i++) {
			list.add(test[i]);
		}
		List<List<String>> res = findMinPaths(start, end, list);
		for (List<String> obj : res) {
			for (String str : obj) {
				System.out.print(str + " -> ");
			}
			System.out.println();
		}

	}

}
