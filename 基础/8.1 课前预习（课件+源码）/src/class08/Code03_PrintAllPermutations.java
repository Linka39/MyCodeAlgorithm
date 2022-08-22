package class08;

import java.util.ArrayList;

/**
 * 打印一个字符串的全部排列
 * 打印一个字符串的全部排列，要求不要出现重复的排列（例如abb，全排列时可能会出现多个abb）
 */
public class Code03_PrintAllPermutations {

	public static ArrayList<String> Permutation(String str) {
		ArrayList<String> res = new ArrayList<>();
		if (str == null || str.length() == 0) {
			return res;
		}
		char[] chs = str.toCharArray();
		// 进行全排列
		process(chs, 0, res);
		res.sort(null);
		return res;
	}

	// 策略：全排列就是循环数组，从下标为0的位置其依次进行交换，交换后进行递归，当下标到了数组长度后，输出结果
	public static void process(char[] chs, int i, ArrayList<String> res) {
		// 下标到了数组长度后，输出结果
		if (i == chs.length) {
			res.add(String.valueOf(chs));
			System.out.println(chs);
		}
		// 创建下标为字符的数组，代表有无访问
		boolean[] visit = new boolean[26];
		for (int j = i; j < chs.length; j++) {
			// 如果是相同数组，不进行交换
			if (!visit[chs[j] - 'a']) {
				visit[chs[j] - 'a'] = true;
				swap(chs, i, j);
				process(chs, i + 1, res);
				swap(chs, i, j);
			}
		}
	}

	public static void swap(char[] chs, int i, int j) {
		char tmp = chs[i];
		chs[i] = chs[j];
		chs[j] = tmp;
	}

}
