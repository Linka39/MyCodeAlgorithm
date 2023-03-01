package class05;

/**
 * 牛牛准备参加学校组织的春游, 出发前牛牛准备往背包里装入一些零食, 牛牛的背包容量为w。
 * 牛牛家里一共有n袋零食,第i袋零食体积为v[i]。 牛牛想知道在总体积不超过背包容量的情况下，
 * 他一共有多少种零食放法(总体积为0也 算一种放法)。
 */
public class Problem06_CompriseWays {

	// 请保证arr里面都是正数, w也是正数
	// 思路：使用 dp 数组，记忆搜索法，列为背包大小，行为零食。
	public static int ways(int[] arr, int w) {
		if (arr == null || arr.length == 0 || w < 0) {
			return 0;
		}
		// 初始化，每行第一列为 1，代表不放东西的一种放法。
		int[][] dp = new int[arr.length][w + 1];
		for (int i = 0; i < arr.length; i++) {
			dp[i][0] = 1;
		}
		// 第一行，如果放零食1 并小于背包容量的话代表有 2 种放法
		for (int j = 1; j <= w; j++) {
			dp[0][j] = j >= arr[0] ? 2 : 1;
		}
		// 记忆搜索，拿到不放当前零食的值，如果放得下则加上放下后剩余空间的最优值
		for (int i = 1; i < arr.length; i++) {
			for (int j = 1; j <= w; j++) {
				dp[i][j] = dp[i - 1][j];
				if (j - arr[i] >= 0) {
					dp[i][j] += dp[i - 1][j - arr[i]];
				}
			}
		}
		// 最后一行的最后列则代表最优解
		return dp[arr.length - 1][w];
	}

	public static void main(String[] args) {
		int[] arr = { 4, 3, 2, 9 };
		int w = 8;
		System.out.println(ways(arr, w));
	}

}
