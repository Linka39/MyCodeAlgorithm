package class06;

/**
 * 小Q正在给一条长度为n的道路设计路灯安置方案。
 * 为了让问题更简单,小Q把道路视为n个方格,需要照亮的地方用'.'表示, 不需要 照亮的障碍物格子用'X'表示。
 * 小Q现在要在道路上设置一些路灯, 对于安置在 pos位置的路灯, 这盏路灯可以照亮pos - 1, pos, pos + 1这三个位置。
 * 小Q希望能安置尽量少的路灯照亮所有'.'区域, 希望你能帮他计算一下最少需 要多少盏路灯。
 *
 * 输入描述： 输入的第一行包含一个正整数t(1 <= t <= 1000), 表示测试用例数 接下来每两行一个测试数据,
 * 第一行一个正整数n(1 <= n <= 1000),表示道路 的长度。第二行一个字符串s表示道路的构造,只包含'.'和'X'。
 *
 * 输出描述： 对于每个测试用例, 输出一个正整数表示最少需要多少盏路灯。
 */
public class Problem05_Light {

	// 思路：...的话需要一个灯能照亮，..X 要1个，.X 也要1个，....X 则至少要4个
	// 枚举所有情况递归就可以
	public static int minLight1(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		return process(str, 0, true);
	}

	// 当前来到了i位置
	// 函数潜台词：help[0..i-2]上都已经点亮了
	// pre表示i-1位置是否点亮
	// 返回如果把所有位置都点亮，help[i..最后]需要几盏灯
	// process(help, i, true)：表示help[0..i-2]上都已经点亮了，i-1位置也点亮的情况下，help[i..最后]需要几盏灯
	// process(help, i, false)：表示help[0..i-2]上都已经点亮了，但是i-1位置没亮的情况下，help[i..最后]需要几盏灯
	public static int process(char[] help, int i, boolean pre) {
		// base case
		if (i == help.length) {
			return 0;
		}
		// 如果到最后一位的话，i-1位置点亮返回0，没亮返回最大值（由最小值的判断筛选）
		if (i == help.length - 1) {
			return pre ? 0 : Integer.MAX_VALUE;
		}
		int ans = Integer.MAX_VALUE;
		int restLight = 0;
		if (pre) {
			// i-1位置已点亮，当前位置可点亮，为了确保最少点灯，当前位置先不放灯
			if (help[i] == '.') {
				restLight = process(help, i + 1, false);
				if (restLight != Integer.MAX_VALUE) {
					ans = Math.min(ans, restLight);
				}
			// i-1位置已点亮，当前位置为'X'，不可点亮
			} else {
				// 不加灯，i-1位置已点亮，则跳一个位置
				restLight = process(help, i + 1, true);
				if (restLight != Integer.MAX_VALUE) {
					ans = Math.min(ans, restLight);
				}
			}
		} else {
			// i-1位置没亮，当前位置可点亮时，则在当前位置加灯，跳两个位置
			if (help[i] == '.') {
				restLight = process(help, i + 2, true);
				if (restLight != Integer.MAX_VALUE) {
					ans = Math.min(ans, restLight + 1);
				}
			// 当前位置为'X'，不可点亮，则跳一位置
			} else {
				restLight = process(help, i + 1, true);
				if (restLight != Integer.MAX_VALUE) {
					ans = Math.min(ans, restLight + 1);
				}
			}
		}
		return ans;
	}

	// 记忆搜索矩阵，可以将两个变量i, boolean 抽象成一个 i i行2列的矩阵计算
	// 代码逻辑有误，需调整
	public static int minLight2(String s) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		char[] str = s.toCharArray();
		char[] help = new char[str.length + 2];
		help[0] = '.';
		help[str.length] = '.';
		for (int i = 0; i < str.length; i++) {
			help[i + 1] = str[i];
		}
		int[][] dp = new int[help.length + 1][2];
		dp[help.length][0] = 0;
		dp[help.length][1] = 0;
		dp[help.length - 1][0] = Integer.MAX_VALUE;
		dp[help.length - 1][1] = 0;
		for (int i = help.length - 2; i >= 1; i--) {
			dp[i][0] = Integer.MAX_VALUE;
			dp[i][1] = Integer.MAX_VALUE;
			int restLight = 0;
			if (help[i] == '.') {
				restLight = dp[i + 1][1];
				if (restLight != Integer.MAX_VALUE) {
					dp[i][1] = Math.min(dp[i][1], restLight);
				}
			} else {
				restLight = dp[i + 1][0];
				if (restLight != Integer.MAX_VALUE) {
					dp[i][1] = Math.min(dp[i][1], restLight);
				}
				restLight = dp[i + 2][1];
				if (restLight != Integer.MAX_VALUE) {
					dp[i][1] = Math.min(dp[i][1], restLight + 1);
				}
			}
			restLight = dp[i + 2][1];
			if (restLight != Integer.MAX_VALUE) {
				dp[i][0] = Math.min(dp[i][0], restLight + 1);
			}
		}
		return dp[1][1];
	}

	public static void main(String[] args) {
		String test = "...X.X.X..XX.XX.X.X.X.X.XX.XXX.X.XXX.XX";
		test = "..................";
//		test = "XXXXXXXXX.XX";
		System.out.println(minLight1(test));
		System.out.println(minLight2(test));
	}

}
