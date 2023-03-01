package class08;

/**
 * 给定一个只由 0(假)、1(真)、&(逻辑与)、|(逻辑或)和^(异或)五种字符组成 的字符串express，再给定一个布尔值 desired。
 * 返回express能有多少种组合方式，可以达到desired的结果。
 *
 * 【举例】
 * express="1^0|0|1"，desired=false 只有 1^((0|0)|1)和 1^(0|(0|1))的组合可以得到 false，返回 2。
 * express="1"，desired=false 无组合则可以得到false，返回0
 */
public class Problem02_ExpressionNumber {

	// 思路：判断表达式的合法性，合法的话采取递归的方式，以逻辑运算符作为分点，判断两侧为所需要结果的计算方式有多少个，最终累计出有多少返回方式
	// 判断字符串是否合法
	public static boolean isValid(char[] exp) {
		if ((exp.length & 1) == 0) {
			return false;
		}
		// 奇数位的数只能是 0 或 1
		for (int i = 0; i < exp.length; i = i + 2) {
			if ((exp[i] != '1') && (exp[i] != '0')) {
				return false;
			}
		}
		// 偶数位的数只能是逻辑字符
		for (int i = 1; i < exp.length; i = i + 2) {
			if ((exp[i] != '&') && (exp[i] != '|') && (exp[i] != '^')) {
				return false;
			}
		}
		return true;
	}

	public static int num1(String express, boolean desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		if (!isValid(exp)) {
			return 0;
		}
		return p(exp, desired, 0, exp.length - 1);
	}

	// 递归判断表达式有多少种组合方式，有3个变量，L 和 R 位上的字符一定为0或1
	public static int p(char[] exp, boolean desired, int L, int R) {
		// base 变量，desired 为想要的结果
		if (L == R) {
			if (exp[L] == '1') {
				return desired ? 1 : 0;
			} else {
				return desired ? 0 : 1;
			}
		}
		int res = 0;
		if (desired) { // 想要 true 时，分别计算各运算符下的不同情况
			for (int i = L + 1; i < R; i += 2) {
				switch (exp[i]) {
					case '&': // 两边都要 true
						res += p(exp, true, L, i - 1) * p(exp, true, i + 1, R);
						break;
					case '|': // 三种
						res += p(exp, true, L, i - 1) * p(exp, false, i + 1, R);
						res += p(exp, false, L, i - 1) * p(exp, true, i + 1, R);
						res += p(exp, true, L, i - 1) * p(exp, true, i + 1, R);
						break;
					case '^': // 两种，两边不一样时
						res += p(exp, true, L, i - 1) * p(exp, false, i + 1, R);
						res += p(exp, false, L, i - 1) * p(exp, true, i + 1, R);
						break;
				}
			}
		} else {
			for (int i = L + 1; i < R; i += 2) {
				switch (exp[i]) {
				case '&':
					res += p(exp, false, L, i - 1) * p(exp, true, i + 1, R);
					res += p(exp, true, L, i - 1) * p(exp, false, i + 1, R);
					res += p(exp, false, L, i - 1) * p(exp, false, i + 1, R);
					break;
				case '|':
					res += p(exp, false, L, i - 1) * p(exp, false, i + 1, R);
					break;
				case '^': // 两种，两边一样时
					res += p(exp, true, L, i - 1) * p(exp, true, i + 1, R);
					res += p(exp, false, L, i - 1) * p(exp, false, i + 1, R);
					break;
				}
			}
		}
		// 返回累计结果
		return res;
	}

	// 动态规划，记忆搜索法
	public static int num2(String express, boolean desired) {
		if (express == null || express.equals("")) {
			return 0;
		}
		char[] exp = express.toCharArray();
		if (!isValid(exp)) {
			return 0;
		}
		int[][] t = new int[exp.length][exp.length];
		int[][] f = new int[exp.length][exp.length];
		t[0][0] = exp[0] == '0' ? 0 : 1;
		f[0][0] = exp[0] == '1' ? 0 : 1;
		for (int i = 2; i < exp.length; i += 2) {
			t[i][i] = exp[i] == '0' ? 0 : 1;
			f[i][i] = exp[i] == '1' ? 0 : 1;
			for (int j = i - 2; j >= 0; j -= 2) {
				for (int k = j; k < i; k += 2) {
					if (exp[k + 1] == '&') {
						t[j][i] += t[j][k] * t[k + 2][i];
						f[j][i] += (f[j][k] + t[j][k]) * f[k + 2][i] + f[j][k] * t[k + 2][i];
					} else if (exp[k + 1] == '|') {
						t[j][i] += (f[j][k] + t[j][k]) * t[k + 2][i] + t[j][k] * f[k + 2][i];
						f[j][i] += f[j][k] * f[k + 2][i];
					} else {
						t[j][i] += f[j][k] * t[k + 2][i] + t[j][k] * f[k + 2][i];
						f[j][i] += f[j][k] * f[k + 2][i] + t[j][k] * t[k + 2][i];
					}
				}
			}
		}
		return desired ? t[0][t.length - 1] : f[0][f.length - 1];
	}

	public static void main(String[] args) {
		String express = "1^0&0|1&1^0&0^1|0|1&1";
		boolean desired = true;
		System.out.println(num1(express, desired));
		System.out.println(num2(express, desired));

	}

}
