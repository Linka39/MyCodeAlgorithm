package class01;

/**
 * 岛问题
 * 【题目】
 * 一个矩阵中只有0和1两种值，每个位置都可以和自己的上、下、左、右 四个位置相连，如
 * 果有一片1连在一起，这个部分叫做一个岛，求一个矩阵中有多少个岛?
 * 【举例】
 * 001010
 * 111010
 * 100100
 * 000000
 * 这个矩阵中有三个岛
 */
public class Code03_Islands {

	public static int countIslands(int[][] m) {
		if (m == null || m[0] == null) {
			return 0;
		}
		int N = m.length;
		int M = m[0].length;
		int res = 0;
		// 每个位置都要重新进行依次感染计算
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < M; j++) {
				if (m[i][j] == 1) {
					res++;		// 如果未被感染的话，先将结果加一，再进行感染
					infect(m, i, j, N, M);
				}
			}
		}
		return res;
	}

	// 感染函数
	public static void infect(int[][] m, int i, int j, int N, int M) {
		// 确定感染的边界和条件
		if (i < 0 || i >= N || j < 0 || j >= M || m[i][j] != 1) {
			return;
		}
		// 感染过则把当前位置标记为2
		m[i][j] = 2;
		// 从上下左右四个方向开始递归感染
		infect(m, i + 1, j, N, M);
		infect(m, i - 1, j, N, M);
		infect(m, i, j + 1, N, M);
		infect(m, i, j - 1, N, M);
	}

	public static void main(String[] args) {
		int[][] m1 = {  { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				        { 0, 1, 1, 1, 0, 1, 1, 1, 0 },
				        { 0, 1, 1, 1, 0, 0, 0, 1, 0 },
				        { 0, 1, 1, 0, 0, 0, 0, 0, 0 },
				        { 0, 0, 0, 0, 0, 1, 1, 0, 0 },
				        { 0, 0, 0, 0, 1, 1, 1, 0, 0 },
				        { 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
		System.out.println(countIslands(m1));

		int[][] m2 = {  { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						{ 0, 1, 1, 1, 1, 1, 1, 1, 0 },
						{ 0, 1, 1, 1, 0, 0, 0, 1, 0 },
						{ 0, 1, 1, 0, 0, 0, 1, 1, 0 },
						{ 0, 0, 0, 0, 0, 1, 1, 0, 0 },
						{ 0, 0, 0, 0, 1, 1, 1, 0, 0 },
						{ 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
		System.out.println(countIslands(m2));

	}

}
