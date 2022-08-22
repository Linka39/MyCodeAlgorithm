package class07;

/**
 * Bob的生存概率
 * 【题目】 给定五个参数n,m,i,j,k。表示在一个N*M的区域，Bob处在(i,j)点，每次Bob等概率的向上、 下、左、右四个方向移动一步，
 *  Bob必须走K步。如果走完之后，Bob还停留在这个区域上， 就算Bob存活，否则就算Bob死亡。请求解Bob的生存概率，返回字符串表示分数的方式。
 */
public class Code05_BobDie {

	public static String bob1(int N, int M, int i, int j, int K) {
		// 总共可以走的次数
		long all = (long) Math.pow(4, K);
		long live = process(N, M, i, j, K);
		long gcd = gcd(all, live);
		return String.valueOf((live / gcd) + "/" + (all / gcd));
	}

	// 递归法，获取bob可以存活的次数，rest为剩余步数
	public static long process(int N, int M, int row, int col, int rest) {
		if (row < 0 || row == N || col < 0 || col == M) {
			return 0;
		}
		if (rest == 0) {
			return 1;
		}
		// 从四个方向获取生存的次数
		long live = process(N, M, row - 1, col, rest - 1);
		live += process(N, M, row + 1, col, rest - 1);
		live += process(N, M, row, col - 1, rest - 1);
		live += process(N, M, row, col + 1, rest - 1);
		return live;
	}

	// 求出m,n的最大公约数
	public static long gcd(long m, long n) {
		return n == 0 ? m : gcd(n, m % n);
	}

	// 通过表结构法获取bob生存的次数, z轴为步数
	public static String bob2(int N, int M, int i, int j, int K) {
		int[][][] dp = new int[N + 2][M + 2][K + 1];
		for (int row = 1; row <= N; row++) {
			for (int col = 1; col <= M; col++) {
				// 将z轴初始值设为1，代表存活，之后的结果依靠这一行进行推断
				dp[row][col][0] = 1;
			}
		}
		for (int rest = 1; rest <= K; rest++) {
			for (int row = 1; row <= N; row++) {
				for (int col = 1; col <= M; col++) {
					// 通过严格表结构获取通过不同方向的值，进行累加
					dp[row][col][rest] = dp[row - 1][col][rest - 1];
					dp[row][col][rest] += dp[row + 1][col][rest - 1];
					dp[row][col][rest] += dp[row][col - 1][rest - 1];
					dp[row][col][rest] += dp[row][col + 1][rest - 1];
				}
			}
		}
		long all = (long) Math.pow(4, K);
		// 获取K步时存活的次数
		long live = dp[i + 1][j + 1][K];
		long gcd = gcd(all, live); // 得到最大公约数
		// 获取存活比率
		return String.valueOf((live / gcd) + "/" + (all / gcd));
	}

	public static void main(String[] args) {
		int N = 10;
		int M = 10;
		int i = 3;
		int j = 2;
		int K = 5;
		System.out.println(bob1(N, M, i, j, K));
		System.out.println(bob2(N, M, i, j, K));
	}

}
