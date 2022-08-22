package class07;

/**
 * 介绍前缀树
 * 何为前缀树? 如何生成前缀树?
 * 例子:
 * 一个字符串类型的数组arr1，另一个字符串类型的数组arr2。arr2中有哪些字符，是arr1中出现的？请打印。
 * arr2中有哪些字符，是作为arr1中某个字符串前缀出现的？请打印。
 * arr2中有哪些字符，是作为arr1中某个字符串前缀出现的？请打印
 * arr2中出现次数最大的前缀
 */
public class Code01_TrieTree {

	// 定义前缀树的结构，字符定义在路径上
	public static class TrieNode {
		public int path;	// 路过数目
		public int end;		// 结尾数
		public TrieNode[] nexts;  // 类似哈希表，记录在相关字符路径上的节点

		public TrieNode() {
			path = 0;
			end = 0;
			nexts = new TrieNode[26];
		}
	}

	public static class Trie {
		private TrieNode root;

		public Trie() {
			root = new TrieNode();
		}

		// 生成前缀树
		public void insert(String word) {
			if (word == null) {
				return;
			}
			// 转换为字符数组
			char[] chs = word.toCharArray();
			TrieNode node = root;
			int index = 0;
			// 在相应的字符索引上生成树的节点
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					node.nexts[index] = new TrieNode();
				}
				// 节点下移，将相应节点的路过标记数加1
				node = node.nexts[index];
				node.path++;
			}
			// 结尾标记数加1
			node.end++;
		}

		// 如果查到了相应字符的前缀，进行字符删除
		public void delete(String word) {
			if (search(word) != 0) {
				char[] chs = word.toCharArray();
				TrieNode node = root;
				int index = 0;
				// 循环匹配串，看路过标记是否为0
				for (int i = 0; i < chs.length; i++) {
					index = chs[i] - 'a';
					// 减1后，路过标记为0则把引用设为null，触发垃圾回收
					if (--node.nexts[index].path == 0) {
						node.nexts[index] = null;
						return;
					}
					// 指针下移
					node = node.nexts[index];
				}
				node.end--;
			}
		}

		// 匹配串是否在前缀树中出现
		public int search(String word) {
			if (word == null) {
				return 0;
			}
			// 将匹配串转为字符数组
			char[] chs = word.toCharArray();
			TrieNode node = root;
			int index = 0;
			// 看匹配串中的字符是否在前缀树中出现，循环到最后一位，返回在前缀树里节点的end
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			return node.end;
		}

		// 看以pre为前缀的数目有多少
		public int prefixNumber(String pre) {
			if (pre == null) {
				return 0;
			}
			char[] chs = pre.toCharArray();
			TrieNode node = root;
			int index = 0;
			// 将前缀转为数组，在前缀树上下移
			for (int i = 0; i < chs.length; i++) {
				index = chs[i] - 'a';
				if (node.nexts[index] == null) {
					return 0;
				}
				node = node.nexts[index];
			}
			// 最终找到的话，返回路过节点数
			return node.path;
		}
	}

	public static void main(String[] args) {
		Trie trie = new Trie();
		System.out.println(trie.search("zuo"));
		trie.insert("zuo");
		System.out.println(trie.search("zuo"));
		trie.delete("zuo");
		System.out.println(trie.search("zuo"));
		trie.insert("zuo");
		trie.insert("zuo");
		trie.delete("zuo");
		System.out.println(trie.search("zuo"));
		trie.delete("zuo");
		System.out.println(trie.search("zuo"));
		trie.insert("zuoa");
		trie.insert("zuoac");
		trie.insert("zuoab");
		trie.insert("zuoad");
		trie.delete("zuoa");
		System.out.println(trie.search("zuoa"));
		System.out.println(trie.prefixNumber("zuo"));

	}

}
