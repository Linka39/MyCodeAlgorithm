package class08;

/**
 * 矩阵的最小路径和
 * 【题目】 给定一个矩阵 m，从左上角开始每次只能向右或者向下走，最后到达右下角的位置，路径上所有的数字累加起来就是路径和，返回所有的路径中最小的路径和。
 * 【举例】 如果给定的 m 如下:
 * 1359
 * 8134
 * 5061
 * 8840
 * 路径 1，3，1，0，6，1，0 是所有路径中路径和最小的，所以返回12
 */
public class Code01_MinPathSum {

	public static int minPathSum1(int[][] m) {
		// 排除扰乱因素
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int row = m.length;
		int col = m[0].length;
		// 根据矩阵定义dp数组
		int[][] dp = new int[row][col];
		dp[0][0] = m[0][0];
		// dp数组的第一行，第一列都有一个初始值
		for (int i = 1; i < row; i++) {
			dp[i][0] = dp[i - 1][0] + m[i][0];
		}
		for (int j = 1; j < col; j++) {
			dp[0][j] = dp[0][j - 1] + m[0][j];
		}
		// 根据dp数组求出当前位置的值，min(往右,往下) + 当前位置
		for (int i = 1; i < row; i++) {
			for (int j = 1; j < col; j++) {
				dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + m[i][j];
			}
		}
		// 返回右下角的值
		return dp[row - 1][col - 1];
	}

	public static int minPathSum2(int[][] m) {
		if (m == null || m.length == 0 || m[0] == null || m[0].length == 0) {
			return 0;
		}
		int more = Math.max(m.length, m[0].length); //
		int less = Math.min(m.length, m[0].length); //
		boolean rowmore = more == m.length; //
		int[] arr = new int[less]; //
		arr[0] = m[0][0];
		for (int i = 1; i < less; i++) {
			arr[i] = arr[i - 1] + (rowmore ? m[0][i] : m[i][0]);
		}
		for (int i = 1; i < more; i++) {
			arr[0] = arr[0] + (rowmore ? m[i][0] : m[0][i]);
			for (int j = 1; j < less; j++) {
				arr[j] = Math.min(arr[j - 1], arr[j])
						+ (rowmore ? m[i][j] : m[j][i]);
			}
		}
		return arr[less - 1];
	}

	// for test ，生成随机矩阵
	public static int[][] generateRandomMatrix(int rowSize, int colSize) {
		if (rowSize < 0 || colSize < 0) {
			return null;
		}
		int[][] result = new int[rowSize][colSize];
		for (int i = 0; i != result.length; i++) {
			for (int j = 0; j != result[0].length; j++) {
				result[i][j] = (int) (Math.random() * 10);
			}
		}
		return result;
	}

	// for test，打印当前矩阵
	public static void printMatrix(int[][] matrix) {
		for (int i = 0; i != matrix.length; i++) {
			for (int j = 0; j != matrix[0].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		// int[][] m = generateRandomMatrix(3, 4);
		int[][] m = { { 1, 3, 5, 9 }, { 8, 1, 3, 4 }, { 5, 0, 6, 1 },
				{ 8, 8, 4, 0 } };
		printMatrix(m);
		System.out.println(minPathSum1(m));
		System.out.println(minPathSum2(m));

	}
}
