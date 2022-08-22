package class07;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 贪心算法
 * 在某一个标准下，优先考虑最满足标准的样本，最后考虑最不满足标准的样本，最终得到一个答案的算法，叫作贪心算法。
 * 也就是说，不从整体最优上加以考虑，所做出的是在某种意义上的局部最优解。
 * 局部最优  -?->  整体最优
 */

/**
 * 贪心算法的在笔试时的解题套路
 * 1，实现一个不依靠贪心策略的解法X，可以用最暴力的尝试
 * 2，脑补出贪心策略A、贪心策略B、贪心策略C...
 * 3，用解法X和对数器，去验证每一个贪心策略，用实验的方式得知哪个贪心策略正确
 * 4，不要去纠结贪心策略的证明
 */

/**
 * 例子：
 * 给定一个字符串类型的数组strs，找到一种拼接方式，使得把所有字符串拼起来之后形成的字符串具有最小的字典序。
 * 证明贪心策略可能是件非常腌心的事情。平时当然推荐你搞清楚所有的来龙去脉，但是笔试 时用对数器的方式！
 */

/**
 * 贪心策略在实现时，经常使用到的技巧：
 * 1，根据某标准建立一个比较器来排序
 * 2，根据某标准建立一个比较器来组成堆
 */

public class Code02_LowestLexicography {

	// 贪心策略，字符相加，如果 a+b < b+a 那么a在b前面
	public static class MyComparator implements Comparator<String> {
		@Override
		public int compare(String a, String b) {
			return (a + b).compareTo(b + a);
		}
	}

	// 将得到的字符进行拼接
	public static String lowestString(String[] strs) {
		if (strs == null || strs.length == 0) {
			return "";
		}
		// 按比较器的顺序进行排序，得到字符串拼接的最小字典排序
		Arrays.sort(strs, new MyComparator());
		String res = "";
		for (int i = 0; i < strs.length; i++) {
			res += strs[i];
		}
		return res;
	}

	public static void main(String[] args) {
		String[] strs1 = { "jibw", "ji", "jp", "bw", "jibw" };
		System.out.println(lowestString(strs1));

		String[] strs2 = { "ba", "b" };
		System.out.println(lowestString(strs2));

	}

}
