package class05;

/**
 * 字符串只由'0'和'1'两种字符构成， 当字符串长度为1时，所有可能的字符串为"0"、"1"；
 * 当字符串长度为2时，所有可能的字符串为"00"、"01"、"10"、"11"；
 * 当字符串长度为3时，所有可能的字符串为"000"、"001"、"010"、"011"、"100"、 "101"、"110"、"111" ...
 * 如果某一个字符串中，只要是出现'0'的位置，左边就靠着'1'，这样的字符串叫作达 标字符串。
 * 给定一个正数N，返回所有长度为N的字符串中，达标字符串的数量。 比如，N=3，返回3，因为只有"101"、"110"、"111"达标。
 */
public class Problem01_ZeroLeftOneStringNumber {

	// n = 1 有 1 种情况 1
	// n = 2 有 2 种情况 10、11
	// n = 3 有 3 种情况，"101"、"110"、"111"
	// n = 4 有 5 种情况，"1010"、"1011"、"1100"、"1110"、"1111"
	// 思路：可以将问题抽象为一个斐波那契数列，f(n) = f(n-1) + f(n-2)，
	// 推导：最左边的数一定为1，第2位有 0 1 两种情况，当为 0 时第3位只能为1，这种情况可用 f(n-2) 来计算。当为 1 时，这种情况可以用 f(n-1) 来计算

	/**
	 * 斐波那契优化：类似 f(n) = af(n-1) + bf(n-2) + cf(n-3) 这种依赖前值进行计算的公式一定会存在一个 n 项矩阵 N，
	 * 	 如附图：使得矩阵 [f(n) f(n-2) f(n-3)] = [f(3) f(2) f(1)] * N^(n-3)
	 */

	// 解法1：O()=(2^n)-1= O(2^n)复杂度，递归求斐波那契数列，将 f(n-1) + f(n-2) 以递归形式赋值
	public static int getNum1(int n) {
		if (n < 1) {
			return 0;
		}
		return process(1, n);
	}
	public static int process(int i, int n) {
		if (i == n - 1) {
			return 2;
		}
		if (i == n) {
			return 1;
		}
		return process(i + 1, n) + process(i + 2, n);
	}

	// 解法2：O(n)复杂度，使用for循环求解，从 2 开始循环，将前两个值累加作为当前值
	public static int getNum2(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1) {
			return 1;
		}
		int pre = 1;
		int cur = 1;
		int tmp = 0;
		for (int i = 2; i < n + 1; i++) {
			tmp = cur;
			cur += pre;
			pre = tmp;
		}
		return cur;
	}

	// 解法3：O(log n)复杂度，首先通过笔算得到 base 数组作为基矩阵，
	public static int getNum3(int n) {
		if (n < 1) {
			return 0;
		}
		if (n == 1 || n == 2) {
			return n;
		}
		int[][] base = { { 1, 1 }, { 1, 0 } };
		// 计算矩阵的 ^(n-2) 次方
		int[][] res = matrixPower(base, n - 2);
		// 通过推导的公式逆推出 F(N)
		return 2 * res[0][0] + res[1][0];
	}

	// 计算矩阵的幂运算，矩阵 m 的 p 次幂
	public static int[][] matrixPower(int[][] m, int p) {
		// 首先定义一个单位矩阵，从左上角到右下角的对角线（称为主对角线）上的元素均为1。除此以外全都为0。
		int[][] res = new int[m.length][m[0].length];
		for (int i = 0; i < res.length; i++) {
			res[i][i] = 1;
		}
		int[][] tmp = m;
		// 指数每次右移一位，按位与 1 如果不为0，则代表矩阵在当前位数的 2 次幂不为空
		for (; p != 0; p >>= 1) {
			// 不为 0 则将结果乘以当前的二次幂
			if ((p & 1) != 0) {
				res = muliMatrix(res, tmp);
			}
			// 求当前矩阵的二次幂
			tmp = muliMatrix(tmp, tmp);
		}
		return res;
	}

	// 矩阵的相乘算法，最终返回一个结果矩阵
	public static int[][] muliMatrix(int[][] m1, int[][] m2) {
		int[][] res = new int[m1.length][m2[0].length];
		for (int i = 0; i < m1.length; i++) {
			for (int j = 0; j < m2[0].length; j++) {
				for (int k = 0; k < m2.length; k++) {
					res[i][j] += m1[i][k] * m2[k][j];
				}
			}
		}
		return res;
	}

	public static void main(String[] args) {
		for (int i = 0; i != 20; i++) {
			System.out.println(getNum1(i));
			System.out.println(getNum2(i));
			System.out.println(getNum3(i));
			System.out.println("===================");
		}

	}
}
