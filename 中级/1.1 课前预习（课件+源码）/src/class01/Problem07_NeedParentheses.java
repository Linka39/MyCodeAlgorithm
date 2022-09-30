package class01;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * 一个完整的括号字符串定义规则如下:
 * ①空字符串是完整的。
 * ②如果s是完整的字符串，那么(s)也是完整的。
 * ③如果s和t是完整的字符串，将它们连接起来形成的st也是完整的。
 *
 * 例如，"(()())", ""和"(())()"是完整的括号字符串，"())(", "()(" 和 ")" 是不完整的括号字符串。
 * 牛牛有一个括号字符串s,现在需要在其中任意位置尽量少地添加括号,将其转化 为一个完整的括号字符串。请问牛牛至少需要添加多少个括号
 */
public class Problem07_NeedParentheses {

	// 思路：当有'('时，所需的右括号数目leftRest累加，当遇到')'时，leftRest
	// 若只有')'时，needSolveRight 左向位累加
	public static int needParentheses(String str) {
		int leftRest = 0;
		int needSolveRight = 0;
		Character[] chars = {'(', ')'};
		HashSet<Character> charsSet = new HashSet<>(Arrays.asList(chars));
		for (int i = 0; i < str.length(); i++) {
			if (charsSet.contains(str.charAt(i))){

				// 实际代码
				if (str.charAt(i) == '(') {
					leftRest++;
				} else {
					if (leftRest == 0) {
						needSolveRight++;
					} else {
						leftRest--;
					}
				}


			}
		}
		return leftRest + needSolveRight;
	}

	public static void main(String args[]) {
		System.out.println(needParentheses("))(()()))("));
	}

}
