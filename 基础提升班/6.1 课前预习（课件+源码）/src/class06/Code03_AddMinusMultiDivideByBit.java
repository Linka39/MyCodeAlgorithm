package class06;

/**
 * 给定两个有符号32位整数a和b，不能使用算术运算符，分别实现a和b的加、减、乘、除 运算
 * 【要求】
 * 如果给定a、b执行加减乘除的运算结果就会导致数据的溢出，
 * 那么你实现的函数不必对此负责，除此之外请保证计算过程不发生溢
 */
public class Code03_AddMinusMultiDivideByBit {

	public static int add(int a, int b) {
		int sum = a;
		// 如果b == 0，即不能再进位时，运算结束
		while (b != 0) {
			sum = a ^ b;		// 位异或相当于 0 1 相加
			b = (a & b) << 1; 	// 左移相当于 1 1 相加，左进一位
			a = sum;
		}
		return sum;
	}

	// 获取n的相反数（n取反并加1）
	public static int negNum(int n) {
		return add(~n, 1);
	}

	public static int minus(int a, int b) {
		return add(a, negNum(b));
	}

	public static int multi(int a, int b) {
		int res = 0;
		while (b != 0) {
			if ((b & 1) != 0) {
				res = add(res, a);
			}
			a <<= 1;
			b >>>= 1;
		}
		return res;
	}

	public static boolean isNeg(int n) {
		return n < 0;
	}

	public static int div(int a, int b) {
		int x = isNeg(a) ? negNum(a) : a;
		int y = isNeg(b) ? negNum(b) : b;
		int res = 0;
		for (int i = 31; i > -1; i = minus(i, 1)) {
			if ((x >> i) >= y) {
				res |= (1 << i);
				x = minus(x, y << i);
			}
		}
		return isNeg(a) ^ isNeg(b) ? negNum(res) : res;
	}

	public static int divide(int a, int b) {
		if (b == 0) {
			throw new RuntimeException("divisor is 0");
		}
		if (a == Integer.MIN_VALUE && b == Integer.MIN_VALUE) {
			return 1;
		} else if (b == Integer.MIN_VALUE) {
			return 0;
		} else if (a == Integer.MIN_VALUE) {
			int res = div(add(a, 1), b);
			return add(res, div(minus(a, multi(res, b)), b));
		} else {
			return div(a, b);
		}
	}

	public static void main(String[] args) {
		int a = (int) (Math.random() * 100000) - 50000;
		int b = (int) (Math.random() * 100000) - 50000;
		System.out.println("a = " + a + ", b = " + b);
		System.out.println(add(a, b));
		System.out.println(a + b);
		System.out.println("=========");
		System.out.println(minus(a, b));
		System.out.println(a - b);
		System.out.println("=========");
		System.out.println(multi(a, b));
		System.out.println(a * b);
		System.out.println("=========");
		System.out.println(divide(a, b));
		System.out.println(a / b);
		System.out.println("=========");

		a = Integer.MIN_VALUE;
		b = 32;
		System.out.println(divide(a, b));
		System.out.println(a / b);

	}

}
