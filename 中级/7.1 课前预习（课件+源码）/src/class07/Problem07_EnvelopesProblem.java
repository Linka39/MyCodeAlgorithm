package class07;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 最长递增子序列问题，数组中每个下标的数都递增的最大个数
 *
 * Input: envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * Output: 3
 * Explanation: 可递增的最大数为 3 ([2,3] => [5,4] => [6,7]).
 */
public class Problem07_EnvelopesProblem {

	public static class Envelope {
		public int l;
		public int h;

		public Envelope(int weight, int hight) {
			l = weight;
			h = hight;
		}
	}

	// 排序方法，先按照 l 升序，相等再按照 h 升序
	public static class EnvelopeComparator implements Comparator<Envelope> {
		@Override
		public int compare(Envelope o1, Envelope o2) {
			return o1.l != o2.l ? o1.l - o2.l : o1.h - o2.h;
		}
	}

	// 将二维矩阵初始化并排序成一个 Envelope 数组
	public static Envelope[] getSortedEnvelopes(int[][] matrix) {
		Envelope[] res = new Envelope[matrix.length];
		for (int i = 0; i < matrix.length; i++) {
			res[i] = new Envelope(matrix[i][0], matrix[i][1]);
		}
		Arrays.sort(res, new EnvelopeComparator());
		return res;
	}

	// 思路：先按递增规则排序好数组，利用动态规划方法求以当前索引为最后一位时，所能得到的递增序列的最长长度
	public static int maxEnvelopes(int[][] matrix) {
		// 按规则排序好数组
		Envelope[] envelopes = getSortedEnvelopes(matrix);
		// dp 记录使用第i个套娃的最大数量。
		int[] dp = new int[matrix.length];
		int res = 0;
		// 第一个数都是递增的
		for (int i = 1; i < envelopes.length; i++) {
			for(int j = 0;j < i;j++)
			{
				// l 相等的话，h 一定是递增的, 若 dp 数组的值后值大于前值，则说明后面的数找到了更大值，不需要累加了
				if(envelopes[j].l == envelopes[i].l && dp[i] <= dp[j]){
					dp[i] = dp[j] + 1;
				}
				// l 不等，取较大的子序列数量
				if(envelopes[j].l != envelopes[i].l && envelopes[i].h > envelopes[j].h)
				{
					dp[i] = Math.max(dp[i], dp[j] + 1);
				}
			}
			res = Math.max(dp[i], res);
		}
		// 过程中的最大值再加 1(当前值)，为最大递增子序列数量
		return res + 1;
	}

	public static void main(String[] args) {
		int[][] test = { { 5,4 }, { 6,7 }, { 2,5 }, { 6,5 } };
		System.out.println(maxEnvelopes(test));
	}
}
