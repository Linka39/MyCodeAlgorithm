package class06;

/**
 * 给定一个整型矩阵，返回子矩阵的最大累计和。
 * 子矩阵的最大累计和为所有矩阵相加后，再进行相加。
 */
public class Problem07_SubMatrixMaxSum {

	// 思路：假设矩阵为 m 行 n 列，复杂度为O(m^2 * n)，首先矩阵先按行分起始行，再按行分每批包含几行
	// 划分好子矩阵后，再进行矩阵压缩求最大值
	public static int maxSum(int[][] m) {
		if (m == null || m.length == 0 || m[0].length == 0) {
			return 0;
		}
		int max = Integer.MIN_VALUE;
		int cur = 0;
		int[] s = null;
		for (int i = 0; i != m.length; i++) {
			// 将矩阵的列压缩完一个数组
			s = new int[m[0].length];
			for (int j = i; j != m.length; j++) {
				cur = 0;
				// 划分好子矩阵后，确定每个子矩阵包含多少列
				for (int k = 0; k != s.length; k++) {
					// 在每一列中求出自上而下的累计和
					s[k] += m[j][k];
					cur += s[k]; // 和当前值累加并求最大值
					max = Math.max(max, cur);
					cur = cur < 0 ? 0 : cur; // 当前值小于0，则从下一值重新计算
				}
			}
		}
		return max;
	}

	public static void main(String[] args) {
		int[][] matrix = {
				{ -90, 48, 78 },
				{ 64, -40, 64 },
				{ -81, -7, 66 } };
		System.out.println(maxSum(matrix));
	}

}
