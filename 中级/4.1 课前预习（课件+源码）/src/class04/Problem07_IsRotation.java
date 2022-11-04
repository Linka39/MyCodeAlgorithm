package class04;

import com.sun.deploy.util.StringUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 如果一个字符串为str，把字符串str前面任意的部分挪到后面形成的字符串叫 作str的旋转词。
 * 比如str="12345"，str的旋转词有"12345"、"23451"、 "34512"、"45123"和"51234"。
 * 给定两个字符串a和b，请判断a和b是否互为旋转词。
 * 比如： a="cdab"，b="abcd"，返回true。 a="1ab2"，b="ab12"，
 * 返回false。 a="2ab1"，b="ab12"，返回true
 */
public class Problem07_IsRotation {

	// 思路：将其中一个串进行拼接作为匹配串，另一个作为模式串，使用 kmp 算法判断模式串是否匹配
	public static boolean isRotation(String a, String b) {
		if (a == null || b == null || a.length() != b.length()) {
			return false;
		}
		String b2 = b + b;
		return getIndexOf(b2, a) != -1;
	}

	// KMP：通过模式串得到一个next数组，
	// 根据 next 数组的预计算功能来提高模式匹配的效率
	public static int getIndexOf(String s, String m) {
		if (s.length() < m.length()) {
			return -1;
		}
		char[] ss = s.toCharArray();
		char[] ms = m.toCharArray();
		int si = 0;
		int mi = 0;
		int[] next = getNextArray(ms);
		while (si < ss.length && mi < ms.length) {
			if (ss[si] == ms[mi]) {
				si++;
				mi++;
			} else if (next[mi] == -1) {
				si++;
			} else {
				mi = next[mi];
			}
		}
		return mi == ms.length ? si - mi : -1;
	}

	public static int[] getNextArray(char[] ms) {
		if (ms.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[ms.length];
		next[0] = -1;
		next[1] = 0;
		int pos = 2;
		int cn = 0;
		while (pos < next.length) {
			if (ms[pos - 1] == ms[cn]) {
				next[pos++] = ++cn;
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[pos++] = 0;
			}
		}
		return next;
	}

	public static void main(String[] args) {
		String str1 = "yunzuocheng";
		String str2 = "zuochengyun";
		System.out.println(isRotation(str1, str2));

		String[] strs = ",sdf.com,,sd22f.com,".split(",");
		HashSet<String> set = new HashSet<>(Arrays.asList(strs));
		String[] strings = set.stream().toArray(String[]::new);
		for (String str : strings) {
			System.out.println(str);
		}
		System.out.println();
	}

}
