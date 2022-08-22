package class06;

/**
 * 位运算使用
 * 判断一个32位正数是不是2的幂、4的幂
 * 0x55555555 8 * 4  (...100100100)代表一个32位的字符
 */
public class Code02_Power {

	// 是2的幂的话，只能有一个1
	public static boolean is2Power(int n) {
		return (n & (n - 1)) == 0;
	}

	// 4的幂要建立在2的幂的前提上
	public static boolean is4Power(int n) {
		return (n & (n - 1)) == 0 && (n & 0x55555555) != 0;
	}

	public static void main(String[] args) {
		System.out.println(is2Power(8));
		System.out.println(is4Power(16));
	}

}
