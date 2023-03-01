package class06;

import java.util.TreeMap;

/**
 * 给你一个字符串类型的数组arr，譬如：
 * String[] arr = { "b\\cst", "d\\", "a\\d\\e", "a\\b\\c" };
 * 你把这些路径中蕴含的目录结构给画出来，子目录直接列在父目录下面，并比父目录 向右进两格，就像这样:
 * a
 * 	 b
 * 	    c
 * 	 d
 * 	    e
 * b
 * 	 cst
 * d
 * 同一级的需要按字母顺序排列，不能乱。
 */
public class Problem01_GetFolderTree {

	// 思路：采取字典树的方式，根节点为空，子节点的 name 代表路径上的字符。生成字典树后在打印，每高一个深度多打印一个空格。
	public static class Node {
		public String name;
		public TreeMap<String, Node> nextMap; // 表示其他子路径对应的节点

		public Node(String name) {
			this.name = name;
			nextMap = new TreeMap<>();
		}
	}

	// 生成字典树，将字符串按分隔符转为数组，从根节点开始查 nextMap，如果在路径中没有此字符串则加入到 nextMap 中。
	// 再移到子树中继续判断
	public static Node generateFolderTree(String[] folderPaths) {
		Node head = new Node("");
		for (String foldPath : folderPaths) {
			// "\\\\" 代表以"\"作为分隔，多余的\代表转义、正则转义的意思
			String[] paths = foldPath.split("\\\\");
			Node cur = head;
			for (int i = 0; i < paths.length; i++) {
				if (!cur.nextMap.containsKey(paths[i])) {
					cur.nextMap.put(paths[i], new Node(paths[i]));
				}
				cur = cur.nextMap.get(paths[i]);
			}
		}
		return head;
	}

	// 生成字典树后再根据 nextMap 实现深度优先打印
	public static void print(String[] folderPaths) {
		if (folderPaths == null || folderPaths.length == 0) {
			return;
		}
		Node head = generateFolderTree(folderPaths);
		printProcess(head, 0);
	}

	public static void printProcess(Node head, int level) {
		if (level != 0) {
			System.out.println(get2nSpace(level) + head.name);
		}
		for (Node next : head.nextMap.values()) {
			printProcess(next, level + 1);
		}
	}

	// 根据遍历生成对应的空格方法
	public static String get2nSpace(int n) {
		String res = "";
		for (int i = 1; i < n; i++) {
			res += "  ";
		}
		return res;
	}

	public static void main(String[] args) {
		// \\实际代表着‘\’，代表着一个转义
		String[] arr = { "b\\cst", "d\\", "a\\d\\e", "a\\b\\c" };
		print(arr);
	}

}
