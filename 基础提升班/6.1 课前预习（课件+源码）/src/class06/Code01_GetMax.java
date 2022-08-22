package class06;

/**
 * 位运算的题目  
 * 之前介绍过一些，下面继续  
 * 给定两个有符号32位整数a和b，返回a和b中较大的。 
 * 【要求】
 * 不用做任何比较判断
 */
public class Code01_GetMax {

	// 每一位不同则为1
	public static int flip(int n) {
		// 相当于对最后一位取反
		return n ^ 1;
	}

	// 返回符号位，如果为0代表负数，1代表正数
	public static int sign(int n) {
		return flip((n >> 31) & 1);
	}

	public static int getMax1(int a, int b) {
		int c = a - b;
		int scA = sign(c);  // 如果a<b, c < 0, scA为0, scB为1，则最大值就为scB
		int scB = flip(scA);
		return a * scA + b * scB;
	}

	public static int getMax2(int a, int b) {
		int c = a - b;
		int sa = sign(a);
		int sb = sign(b);
		int sc = sign(c);
		int difSab = sa ^ sb;
		int sameSab = flip(difSab);
		int returnA = difSab * sa + sameSab * sc;
		int returnB = flip(returnA);
		return a * returnA + b * returnB;
	}

	public static void main(String[] args) {
		int a = -16;
		int b = 44;
		System.out.println(getMax(a, b));
		System.out.println(getMax1(a, b));
		System.out.println(getMax2(a, b));
		a = 2147483647;
		b = -2147480000;
		System.out.println(getMax(a, b));
		System.out.println(getMax1(a, b)); // wrong answer because of overflow
		System.out.println(getMax2(a, b));

	}

	public static int getMax(int a, int b) {
		int c = a - b;
		int notify = (c >> 31) & 1;  // 总有一个为0的
		return (notify ^ 1) * a + notify * b;
	}

}
