

public class Code09_FindOneLessValueIndex {

	/**
	 * 3）求局部最小值问题
	 * @param arr
	 * @return
	 */
	public static int getLessIndex(int[] arr) {
		if (arr == null || arr.length == 0) {
			return -1; // no exist
		}
		// 数组长度为1，不考虑
		if (arr.length == 1 || arr[0] < arr[1]) {
			return 0;
		}
		// 数组长度为2，不考虑
		if (arr[arr.length - 1] < arr[arr.length - 2]) {
			return arr.length - 1;
		}
		int left = 1;
		int right = arr.length - 2;
		int mid = 0;
		// 找极小值 logN 的时间复杂度
		while (left < right) {
			mid = (left + right) / 2;
			// mid大于左边的数，曲度为“↗”，代表极小值在左边
			if (arr[mid] > arr[mid - 1]) {
				right = mid - 1;
			// mid小于右边的数，曲度为“↘”，代表极小值还在右边
			} else if (arr[mid] > arr[mid + 1]) {
				left = mid + 1;
			} else {
				return mid;
			}
		}
		return left;
	}

	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr = { 6, 5, 3, 4, 6, 7, 8 };
		printArray(arr);
		int index = getLessIndex(arr);
		System.out.println("index: " + index + ", value: " + arr[index]);

	}

}
