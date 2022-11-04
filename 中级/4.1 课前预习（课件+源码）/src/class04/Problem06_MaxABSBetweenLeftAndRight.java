package class04;

/**
 * 给定一个数组arr长度为N，你可以把任意长度大于0且小于N的前缀作为左部分，剩下的 作为右部分。
 *
 * 但是每种划分下都有左部分的最大值和右部分的最大值，请返回最大的，
 * 求出数组在某个下标上，左部分最大值减去右部分最大值的绝对值
 */
public class Problem06_MaxABSBetweenLeftAndRight {

	public static int maxABS1(int[] arr) {
		int res = Integer.MIN_VALUE;
		int maxLeft = 0;
		int maxRight = 0;
		for (int i = 0; i != arr.length - 1; i++) {
			maxLeft = Integer.MIN_VALUE;
			for (int j = 0; j != i + 1; j++) {
				maxLeft = Math.max(arr[j], maxLeft);
			}
			maxRight = Integer.MIN_VALUE;
			for (int j = i + 1; j != arr.length; j++) {
				maxRight = Math.max(arr[j], maxRight);
			}
			res = Math.max(Math.abs(maxLeft - maxRight), res);
		}
		return res;
	}

	// 思路：采取预计算的方式，用 lArr 记录从左到右中每个下标的最大值，用 rArr 记录从右到左中每个下标的最大值。
	// 遍历下标，求出 Math.abs(lArr[i] - rArr[i + 1]) 中的最大值。
	public static int maxABS2(int[] arr) {
		int[] lArr = new int[arr.length];
		int[] rArr = new int[arr.length];
		lArr[0] = arr[0];
		rArr[arr.length - 1] = arr[arr.length - 1];
		for (int i = 1; i < arr.length; i++) {
			lArr[i] = Math.max(lArr[i - 1], arr[i]);
		}
		for (int i = arr.length - 2; i > -1; i--) {
			rArr[i] = Math.max(rArr[i + 1], arr[i]);
		}
		int max = 0;
		for (int i = 0; i < arr.length - 1; i++) {
			max = Math.max(max, Math.abs(lArr[i] - rArr[i + 1]));
		}
		return max;
	}

	// 思路3：回归本质，求出数组中的最大值 max, 使用max - 数组两端较小的那个值，得到最大的结果
	public static int maxABS3(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			max = Math.max(arr[i], max);
		}
		return max - Math.min(arr[0], arr[arr.length - 1]);
	}

	public static int[] generateRandomArray(int length) {
		int[] arr = new int[length];
		for (int i = 0; i != arr.length; i++) {
			arr[i] = (int) (Math.random() * 1000) - 499;
		}
		return arr;
	}

	public static void main(String[] args) {
		int[] arr = generateRandomArray(200);
		System.out.println(maxABS1(arr));
		System.out.println(maxABS2(arr));
		System.out.println(maxABS3(arr));
	}
}
