package class08;

/**
 * 给定两个长度都为N的数组weights和values，weights[i]和values[i]分别代表 i号物品的重量和价值。
 * 给定一个正数bag，表示一个载重bag的袋子，你装的物 品不能超过这个重量。
 * 返回你能装下最多的价值是多少
 */
public class Code07_Knapsack {

	public static int maxValue1(int[] weights, int[] values, int bag) {
		return process1(weights, values, 0, 0,  0, bag);
	}

	// 策略：递归获取最大价值，最大重量超过限制返回0
	// 已有重量的价值加入新的价值 与 不加新的价值 相比，获取最大值
	public static int process1(int[] weights, int[] values, int i, int alreadyvalue, int alreadyweight, int bag) {

		if (alreadyweight > bag) {
			return 0;
		}
		if (i >= weights.length) {
			return alreadyvalue;
		}
		// 局部最大值，看加上价值高还是不加高
		return Math.max(
				process1(weights, values, i + 1, alreadyvalue, alreadyweight, bag),
				process1(weights, values, i + 1, alreadyvalue + values[i],  alreadyweight + weights[i], bag));
	}

	public static int maxValue2(int[] c, int[] p, int bag) {
		int[][] dp = new int[c.length + 1][bag + 1];
		for (int i = 0; i <= c.length - 1; i++) {
			for (int j = 0; j <= bag; j++) {
				// dp[i][j]：当有i个物品时，在(j)容量下所能乘放的最大价值
				dp[i + 1][j] = dp[i][j];
				if (j > c[i]) {
					// 已有重量的价值加入新的价值(对应bag增加) 与 不加新的价值(当前容量价值) 相比，获取最大值
					dp[i + 1][j] = Math.max(dp[i + 1][j], p[i] + dp[i][j - c[i]]);
				}
			}
		}
//		for (int i = c.length - 1; i >= 0; i--) {
//			for (int j = bag; j >= 0; j--) {
//				// dp[i][j] 先默认当前容量价值
//				dp[i][j] = dp[i + 1][j];
//				if (j + c[i] <= bag) {
//					// 已有重量的价值加入新的价值(对应bag增加) 与 不加新的价值(当前容量价值) 相比，获取最大值
//					dp[i][j] = Math.max(dp[i][j], p[i] + dp[i + 1][j + c[i]]);
//				}
//			}
//		}
		for (int i = 0; i < dp.length; i++) {
			for (int j = 0; j < dp[0].length; j++) {
				System.out.print(dp[i][j] + "\t");
			}
			System.out.println();
		}
//		return dp[0][0];
		return dp[dp.length - 1][dp[0].length - 1];
	}

	public static void main(String[] args) {
		int[] weights = { 3, 5, 2, 4, 7 };
		int[] values = { 5, 8, 6, 3, 19 };
		int bag = 13;
		System.out.println(maxValue1(weights, values, bag));
		System.out.println(maxValue2(weights, values, bag));
	}

}
