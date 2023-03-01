package class05;

/**
 * 给定一个字符串，如果该字符串符合人们日常书写一个整数的形式，返回int类型的这个数；
 * 如果不符合或者越界返回 -1 或者报错
 */
public class Problem04_ConvertStringToInteger {

	// 思路：如果字符串符合书写习惯，则将字符串安负数计算，负数比正数多1（正数比负数多表示了一个0，负数减一在求反码得正数，正数反码再补码得负数）
	public static int convert(String str) {
		if (str == null || str.equals("")) {
			return 0; // can not convert
		}
		char[] chas = str.toCharArray();
		// 不符合书写习惯直接抛出
		if (!isValid(chas)) {
			return 0; // can not convert
		}
		// 看符号位为多少
		boolean posi = chas[0] == '-' ? false : true;
		int minq = Integer.MIN_VALUE / 10; //  十位数最大
		int minr = Integer.MIN_VALUE % 10; // 个位数最大
		int res = 0;
		int cur = 0;
		for (int i = posi ? 0 : 1; i < chas.length; i++) {
			cur = '0' - chas[i];
			// 判断十位数，以及个位数是否超过最大值
			if ((res < minq) || (res == minq && cur < minr)) {
				return 0; // can not convert
			}
			res = res * 10 + cur;
		}
		if (posi && res == Integer.MIN_VALUE) {
			return 0; // can not convert
		}
		// 正数取其相反数，正数直接取
		return posi ? -res : res;
	}

	// 判断书写是否符合数字的习惯
	public static boolean isValid(char[] chas) {
		if (chas[0] != '-' && (chas[0] < '0' || chas[0] > '9')) {
			return false;
		}
		if (chas[0] == '-' && (chas.length == 1 || chas[1] == '0')) {
			return false;
		}
		if (chas[0] == '0' && chas.length > 1) {
			return false;
		}
		for (int i = 1; i < chas.length; i++) {
			if (chas[i] < '0' || chas[i] > '9') {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		String test1 = "2147483647"; // max in java
		System.out.println(convert(test1));

		String test2 = "-2147483648"; // min in java
		System.out.println(convert(test2));

		String test3 = "2147483648"; // overflow
		System.out.println(convert(test3));

		String test4 = "-2147483649"; // overflow
		System.out.println(convert(test4));

		String test5 = "-123";
		System.out.println(convert(test5));

	}

}
