package class02;

/**
 * 一个合法的括号匹配序列有以下定义:
 * ①空串""是一个合法的括号匹配序列
 * ②如果"X"和"Y"都是合法的括号匹配序列,"XY"也是一个合法的括号匹配序列
 * ③如果"X"是一个合法的括号匹配序列,那么"(X)"也是一个合法的括号匹配序列
 * ④每个合法的括号序列都可以由以上规则生成。
 * 例如: "","()","()()","((()))"都是合法的括号序列 对于一个合法的括号序列我们又有以下定义它的深度:
 * ①空串""的深度是0
 * ②如果字符串"X"的深度是x,字符串"Y"的深度是y,那么字符串"XY"的深度为 max(x,y)
 *
 * 3、如果"X"的深度是x,那么字符串"(X)"的深度是x+1 例如: "()()()"的深度是1,"((()))"的深度是3。
 * 牛牛现在给你一个合法的括号 序列,需要你计算出其深度。
 */
public class Problem04_ParenthesesDeep {

	// 思路：首先判断当前字符串是否为有效括号，不是的话返回0
	// 是的话，以'('为基准累加，每次计算时记录下最大值max。遇到')'减1
	public static boolean isValid(char[] str) {
		if (str == null || str.equals("")) {
			return false;
		}
		int status = 0;
		// 遍历一遍后如果 status == 0 则代表为有效括号串
		for (int i = 0; i < str.length; i++) {
			if (str[i] != ')' && str[i] != '(') {
				return false;
			}
			if (str[i] == ')' && --status < 0) {
				return false;
			}
			if (str[i] == '(') {
				status++;
			}
		}
		return status == 0;
	}

	public static int deep(String s) {
		char[] str = s.toCharArray();
		if (!isValid(str)) {
			return 0;
		}
		// 如果是有效括号串时，以'('为基准累加，每次计算时记录下最大值max。遇到')'减1
		int count = 0;
		int max = 0;
		for (int i = 0; i < str.length; i++) {
			if (str[i] == '(') {
				max = Math.max(max, ++count);
			} else {
				count--;
			}
		}
		return max;
	}

	public static int maxLength(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		char[] chas = str.toCharArray();
		int[] dp = new int[chas.length];
		int pre = 0;
		int res = 0;
		for (int i = 1; i < chas.length; i++) {
			if (chas[i] == ')') {
				pre = i - dp[i - 1] - 1;
				if (pre >= 0 && chas[pre] == '(') {
					dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
				}
			}
			res = Math.max(res, dp[i]);
		}
		return res;
	}

	public static void main(String[] args) {
		String test = "((()))";
		System.out.println(deep(test));

	}

}
