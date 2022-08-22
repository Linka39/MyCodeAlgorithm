package class03;

/**
 * Manacher算法解决的问题  解决回文的问题 abcba相当于一个回文
 * 字符串str中，最长回文子串的长度如何求解？
 * 如何做到时间复杂度O(N)完成
 */
public class Code02_Manacher {

	// 将字符串转换为manacher字符串，加入虚拟字符'#'(只涉及到虚拟字符的比对，如果原字符中有'#'，不影响原值)
	public static char[] manacherString(String str) {
		char[] charArr = str.toCharArray();
		char[] res = new char[str.length() * 2 + 1];	// 新的字符
		int index = 0;
		for (int i = 0; i != res.length; i++) {
			// 偶数下标上塞入虚拟字符
			res[i] = (i & 1) == 0 ? '#' : charArr[index++];
		}
		return res;
	}

	// 获取到最大回文
	public static int maxLcpsLength(String str) {
		if (str == null || str.length() == 0) {
			return 0;
		}
		char[] charArr = manacherString(str); // 每个下标对应的最长回文长度，默认为0
		int[] pArr = new int[charArr.length];
		int C = -1; // 中心点
		int R = -1; // 回文右边界下标
		int max = Integer.MIN_VALUE;
		for (int i = 0; i != charArr.length; i++) {
			// 1、R <= i，在边界外的情况，半径数组设为1
			// 2、R > i，在边界内的话，取回文结构中的较小值，pArr[2 * C - i]代表左回文长度(半径长度)，R - i 代表右回文结构(半径长度)
			// 注：2 * C - i 代表左回文的下标位置（如果i为下标是回文的话，i关于C的对称C-(i-C)一定也是回文结构）
			pArr[i] = R > i ? Math.min(pArr[2 * C - i], R - i) : 1;
			// 左右边界都未越界时，pArr的长度可以为对应一侧的回文长度(加辅助字符)
			while (i + pArr[i] < charArr.length && i - pArr[i] > -1) {
				// 以i为中心，如果对称字符相等的话，对应回文数量加1，不等的话打破循环
				if (charArr[i + pArr[i]] == charArr[i - pArr[i]])
					pArr[i]++;
				else {
					break;
				}
			}
			// 当前下标的回文超过原回文右边界的话，右边界和中心下标进行更新
			if (i + pArr[i] > R) {
				R = i + pArr[i];
				C = i;
			}
			max = Math.max(max, pArr[i]);
		}
		return max - 1;
	}
	public static void main(String[] args) {
		String str1 = "abc1234321ab";
		System.out.println(maxLcpsLength(str1));
	}

	public static void testManacher(){
		String str1 = "abc1234321ab";
		// #1#2#2#1#
		System.out.println(maxLcpsLength2(str1));
	}

	public static int maxLcpsLength2(String str){
		char[] chars = getformatStr(str).toCharArray();
		int max = Integer.MIN_VALUE;
		int[] num = new int[chars.length];
		int R = -1;
		int M = 0;
		int p = 0;
		for (int i = 1; i < chars.length; i++) {
			// 最小为1
			p = 1;
			if (i < R){
				// 利用之前计算过的回文进行加速
				p = Math.min(num[M - (i - M)], R - i);
			}
			while (i + p < chars.length && i - p >= 0) {
				if (chars[i + p] == chars[i - p]){
					p++;
				}else {
					break;
				}
			}
			if (i + p > R){
				R = i + p;
				M = i;
			}
			max = Math.max(max, p);
			num[i] = p;
		}
		return max - 1;
	}

	public static String getformatStr(String str) {
		char[] s1 = str.toCharArray();
		char[] s2 = new char[s1.length * 2 + 1];
		int j = 0;
		s2[j++] = '#';
		for (int i = 0; i < s1.length; i++) {
			s2[j++] = s1[i];
			s2[j++] = '#';
		}
		return String.valueOf(s2);
	}

}
