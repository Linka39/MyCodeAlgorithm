package class07;

/**
 * CC里面有一个土豪很喜欢一位女直播Kiki唱歌，平时就经常给她点赞、送礼、私聊。最近CC直播平台在举行中秋之星主播唱歌比赛，
 * 假设一开始该女主播的初始人气值为start， 能够晋升下一轮人气需要刚好达到end，
 * 土豪给主播增加人气的可以采取的方法有：
 * a. 点赞 花费x C币，人气 + 2    b. 送礼 花费y C币，人气 * 2     c. 私聊 花费z C币，人气 - 2
 * 其中 end 远大于start且end为偶数，
 * 请写一个程序帮助土豪计算一下，最少花费多少C币就能帮助该主播 Kiki将人气刚好达到end，从而能够晋级下一轮？
 *
 * 输入描述： 第一行输入5个数据，分别为：x y z start end，每项数据以空格分开。 其中：0＜x, y, z＜＝10000， 0＜start, end＜＝1000000
 * 输出描述： 需要花费的最少C币。
 * 示例1:
 * 输入 3 100 1 2 6
 * 输出 6
 */
public class Problem04_Kiki {

	// 思路：分支限界法，对递归进行减枝操作
	public static int minCcoins1(int add, int times, int del, int start, int end) {
		if (start > end) {
			return -1;
		}
		// 通过业务分析找到业务的界限
		// 限制的最大目标 end * 2，  （在目标值*2后，再增加就没有意义了）
		// 最大金币 ((end - start) / 2) * add   （只用累加就可以的最大金钱）
		return process(0, end, add, times, del, start, end * 2, ((end - start) / 2) * add);
	}

	// 根据定好的限制条件进行递归
	public static int process(int pre, int aim, int add, int times, int del, int finish, int limitAim, int limitCoin) {
		// 当越过限制条件时，返回最大值
		if (pre > limitCoin) {
			return Integer.MAX_VALUE;
		}
		if (aim < 0) {
			return Integer.MAX_VALUE;
		}
		if (aim > limitAim) {
			return Integer.MAX_VALUE;
		}
		// 等于目标值时返回
		if (aim == finish) {
			return pre;
		}
		int min = Integer.MAX_VALUE;
		// 用 +2 的方式
		int p1 = process(pre + add, aim - 2, add, times, del, finish, limitAim, limitCoin);
		if (p1 != Integer.MAX_VALUE) {
			min = p1;
		}
		// 用 -2 的方式
		int p2 = process(pre + del, aim + 2, add, times, del, finish, limitAim, limitCoin);
		if (p2 != Integer.MAX_VALUE) {
			min = Math.min(min, p2);
		}
		// 用 *2 的方式
		if ((aim & 1) == 0) {
			int p3 = process(pre + times, aim / 2, add, times, del, finish, limitAim, limitCoin);
			// 当不为最大值时，即决策成功，获取两种决策中的较小值
			if (p3 != Integer.MAX_VALUE) {
				min = Math.min(min, p3);
			}
		}
		// 在以上结果递归回调后，返回最小的那一个决策
		return min;
	}

	// 思路2：根据递归的两个变量，之前人气，之后人气，可以形成一个二维矩阵的记忆搜索法
	public static int minCcoins2(int add, int times, int del, int start, int end) {
		if (start > end) {
			return -1;
		}
		int limitCoin = ((end - start) / 2) * add;
		int limitAim = end * 2;
		int[][] dp = new int[limitCoin + 1][limitAim + 1];
		for (int pre = 0; pre <= limitCoin; pre++) {
			for (int aim = 0; aim <= limitAim; aim++) {
				if (aim == start) {
					dp[pre][aim] = pre;
				} else {
					dp[pre][aim] = Integer.MAX_VALUE;
				}
			}
		}
		for (int pre = limitCoin; pre >= 0; pre--) {
			for (int aim = 0; aim <= limitAim; aim++) {
				if (aim - 2 >= 0 && pre + add <= limitCoin) {
					dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + add][aim - 2]);
				}
				if (aim + 2 <= limitAim && pre + del <= limitCoin) {
					dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + del][aim + 2]);
				}
				if ((aim & 1) == 0) {
					if (aim / 2 >= 0 && pre + times <= limitCoin) {
						dp[pre][aim] = Math.min(dp[pre][aim], dp[pre + times][aim / 2]);
					}
				}
			}
		}
		return dp[0][end];
	}

	public static void main(String[] args) {
		int add = 6;
		int times = 5;
		int del = 1;
		int start = 10;
		int end = 30;
		System.out.println(minCcoins1(add, times, del, start, end));
		System.out.println(minCcoins2(add, times, del, start, end));
	}

}
