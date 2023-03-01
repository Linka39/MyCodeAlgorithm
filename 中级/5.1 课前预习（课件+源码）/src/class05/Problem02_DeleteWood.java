package class05;

/*
 *
 * 在迷迷糊糊的大草原上，小红捡到了n根木棍，第i根木棍的长度为i，
 * 小红现在很开心。想选出其中的三根木棍组成美丽的三角形。
 * 但是小明想捉弄小红，想去掉一些木棍，使得小红任意选三根木棍都不能组成三角形。
 * 请问小明最少去掉多少根木棍呢？
 *
 * */
public class Problem02_DeleteWood {

	// 思路：当火柴中 >= 任意两根火柴相加的话，就不能组成三角形。
	// 类似斐波那契，只有 f(n) 等于前两个值的和就不能组成三角形
	public static int minDelete(int m) {
		if (m < 4) {
			return 0;
		}
		int k_2 = 2;
		int k_1 = 3;
		int num = 3; // 不能组成三角形的火柴数量
		while (k_2 + k_1 <= m) {
			num++; // 当遇到不能组成三角的火柴数量加1，k1 和 k2 也进行递增
			k_1 += k_2;
			k_2 = k_1 - k_2;
		}
		return m - num;
	}

	public static void main(String[] args) {
		int test = 8;
		System.out.println(minDelete(test));
	}
}
