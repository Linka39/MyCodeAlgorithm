package class03;

/**
 * KMP算法解决的问题
 * 字符串str1和str2，str1是否包含str2，如果包含返回str2在str1中开始的位置。
 * 如何做到时间复杂度O(N)完成？
 */
public class Code01_KMP {

	public static int getIndexOf(String s, String m) {
		if (s == null || m == null || m.length() < 1 || s.length() < m.length()) {
			return -1;
		}
		char[] str1 = s.toCharArray();	// 匹配串
		char[] str2 = m.toCharArray();	// 模式串
		int i1 = 0;
		int i2 = 0;
		// 获取模式串的next数组
		int[] next = getNextArray(str2);
		// 都没遍历到最后一位则继续遍历，如果相等的话下标共同加一，
		// 如果不等的话，模式串回到next数组位置，若对应下标值为-1(代表首字符)，匹配串加1，否则从模式串位置匹配
		while (i1 < str1.length && i2 < str2.length) {
			if (str1[i1] == str2[i2]) {
				i1++;
				i2++;
			} else if (next[i2] == -1) {
				i1++;
			} else {
				i2 = next[i2];
			}
		}
		// 如果模式串到尾了，返回在匹配串中的索引
		return i2 == str2.length ? i1 - i2 : -1;
	}

	// 获取模式串的next数组
	public static int[] getNextArray(char[] ms) {
		if (ms.length == 1) {
			return new int[] { -1 };
		}
		int[] next = new int[ms.length];
		// 默认将前两个下标设置为-1，0
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int cn = 0;	// 前缀的末位字符
		// 如果当前下标字符 和 next数组对应下标上字符相等，next当前下标的值为cn++
		while (i < next.length) {
			if (ms[i - 1] == ms[cn]) {
				next[i++] = ++cn;
				// cn不为0
			} else if (cn > 0) {
				cn = next[cn];
			} else {
				next[i++] = 0;
			}
		}
		return next;
	}

	public static void main(String[] args) {
		String str = "abcabcababaccc";
		String match = "ababa";
		System.out.println(getIndexOf(str, match));
	}

	public static void testKMP(){
		String str = "abcabcababaccc";
		String match = "ababa";
		System.out.println(myGetIndexOf(str, match));
	}

	public static int myGetIndexOf(String pStr, String mStr){
		if (pStr == null || mStr == null || mStr.isEmpty() || pStr.isEmpty() || pStr.length() < mStr.length()){
			return -1;
		}
		char[] str1 = pStr.toCharArray();
		char[] str2 = mStr.toCharArray();
		int[] next = myGetNext1(mStr);
		int pl = 0;
		int ml = 0;
		int k = 0;
		while (pl < str1.length){
			if (ml == str2.length){
				return pl - ml;
			}
			if (str1[pl] == str2[ml]){
				pl++;
				ml++;
			} else {
				k = next[ml];
				if (next[ml] == 0){
					pl++;
					ml = 0;
				} else {
					ml = k;
				}
			}
		}

		return -1;
	}

	public static int[] myGetNext1(String str) {
		int[] next = new int[str.length()];
		char[] arr = str.toCharArray();
		next[0] = -1;
		next[1] = 0;
		int i = 2;
		int k = 0;
		while (i < next.length) {
			k = next[i-1];
			while (k >= 0) {
				if (arr[k] == arr[i - 1]) {
					next[i] = k + 1;
					break;
				}else {
					k = next[k];
				}
			}
			i++;
		}
		return next;
	}

}
