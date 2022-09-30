package class03;

/**
 * 假设s和m初始化，s = "a"; m = s; 再定义两种操作，
 * 第一种操作： m = s; s = s + s;
 * 第二种操作： s = s + m;
 * 求最小的操作步骤数，可以将s拼接到长度等于n
 */
public class Problem06_SplitNbySM {

	// 思路: 由1开始，s 分别为2s 和 s+m，第二个操作能让操作一数值变得更大
	// 操作二可以认为因子求和，操作一认为是因子个数，最小值为 sum - 个数

	// 附加题：怎么判断一个数是不是质数？
	public static boolean isPrim(int n) {
		if (n < 2) {
			return false;
		}
		int max = (int) Math.sqrt((double) n);
		for (int i = 2; i <= max; i++) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}

	// 请保证n不是质数
	// 返回:
	// 0) 所有因子的和，但是因子不包括1
	// 1) 所有因子的个数，但是因子不包括1
	public static int[] divsSumAndCount(int n) {
		int sum = 0;
		int count = 0;
		for (int i = 2; i <= n; i++) {
			// 首先求出一共包含多少带2的因子，如果可以整除，修改因子的和与个数
			while (n % i == 0) {
				sum += i;
				count++;
				n /= i;
			}
		}
		return new int[] { sum, count };
	}

	public static int minOps(int n) {
		if (n < 2) {
			return 0;
		}
		if (isPrim(n)) {
			return n - 1;
		}
		int[] divSumAndCount = divsSumAndCount(n);
		// 最小因子的和，减去因子个数可以得到操作数
		// 因子个数越少，操作数越大
		return divSumAndCount[0] - divSumAndCount[1];
	}

	public static void main(String[] args) {
		System.out.println(minOps(8));
	}

}
