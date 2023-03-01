package class08;

/**
 * 在数据加密和数据压缩中常需要对特殊的字符串进行编码。给定的字母表A由26个小写英文字母组成，即 A={a, b...z}。
 * 该字母表产生的长序字符串是指定字符串中字母从左到右出现的次序与字母在字母表中出现的次序相同，且每个字符最多出现1次。
 *
 * 例如，a，b，ab，bc，xyz等字符串是升序字符串。
 * 对字母表A产生的所有长度不超过6的升序字符串按照字典排列编码如下：
 * a(1)，b(2)，c(3)……，z(26)，ab(27)， ac(28)……对于任意长度不超过16的升序字符串，迅速计算出它在上述字典中的编码。
 *
 * 输入描述： 第1行是一个正整数N，表示接下来共有N行，在接下来的N行中，每行给出一个字符串。
 * 输出描述： 输出N行，每行对应于一个字符串编码。
 *
 * 示例1:
 * 输入 3 a b ab
 * 输出 1 2 27
 */
public class Problem03_StringToKth {

	// 思路：设计一个函数能找出当前长度以某字符为开头形成的升序串数量，和每个长度下可形成的升序串数量。
	// 求出之前长度的的升序串数量，当前长度下不超过输入字符串的升序串数量依次累加，直到输入串最后一位，再加 1 得到输入串所处的位置

	// 第i个字符开头、长度为len的所有升序字符串有几个
	public static int f(int i, int len) {
		int sum = 0;
		if (len == 1) {
			return 1;
		}
		// 求出当前长度下可组成的升序字符串有多少
		for (int j = i + 1; j <= 26; j++) {
			sum += f(j, len - 1);
		}
		return sum;

	}

	// 长度为len的字符串有多少个
	public static int g(int len) {
		int sum = 0;
		// 以每个字符作为开头能形成的数量累加
		for (int i = 1; i <= 26; i++) {
			sum += f(i, len);
		}
		return sum;
	}

	public static int kth(String s) {
		char[] str = s.toCharArray();
		int sum = 0;
		int len = str.length;
		// 求出之前长度的总数量
		for (int i = 1; i < len; i++) {
			sum += g(i);
		}
		int first = str[0] - 'a' + 1;
		// 当前长度下 first 之前的字符作为首字符的数量
		for (int i = 1; i < first; i++) {
			sum += f(i, len);
		}
		int pre = first;
		// 求str子串的升序串数量
		for (int i = 1; i < len; i++) {
			int cur = str[i] - 'a' + 1;
			for (int j = pre + 1; j < cur; j++) {
				sum += f(j, len - i);
			}
			pre = cur; // 前字符赋值
		}
		return sum + 1;
	}

	public static void main(String[] args) {
		String test = "bc";
		System.out.println(kth(test));
	}

}
