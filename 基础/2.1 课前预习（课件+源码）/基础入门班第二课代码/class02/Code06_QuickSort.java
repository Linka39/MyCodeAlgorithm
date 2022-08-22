

import java.util.Arrays;

/**
 * 荷兰国旗问题
 * 问题一
 * 给定一个数组arr，和一个数num，请把小于等于num的数放在数 组的左边，大于num的
 * 数放在数组的右边。要求额外空间复杂度O(1)，时间复杂度O(N)
 *
 * 问题二(荷兰国旗问题)
 * 给定一个数组arr，和一个数num，请把小于num的数放在数组的 左边，等于num的数放
 * 在数组的中间，大于num的数放在数组的 右边。要求额外空间复杂度O(1)，时间复杂度
 * O(N)
 */

/**
 * 随机快速排序（改进的快速排序）
 * 1）在数组范围中，等概率随机选一个数作为划分值，然后把数组通过荷兰国旗问题分
 * 成三个部分：
 * 左侧<划分值、中间==划分值、右侧>划分值
 * 2）对左侧范围和右侧范围，递归执行
 * 3）时间复杂度为O(N*logN)
 */
public class Code06_QuickSort {

	public static void quickSort(int[] arr) {
		if (arr == null || arr.length < 2) {
			return;
		}
		quickSort(arr, 0, arr.length - 1);
	}

	public static void quickSort(int[] arr, int l, int r) {
		if (l < r) {
			swap(arr, l + (int) (Math.random() * (r - l + 1)), r);
			int[] p = partition(arr, l, r);
			quickSort(arr, l, p[0] - 1);
			quickSort(arr, p[1] + 1, r);
		}
	}

	public static int[] partition(int[] arr, int l, int r) {
		int less = l - 1;// 小边界指针，l 代表活动指针
		int more = r;// 大边界指针
		while (l < more) {
			// 此快排都是从最左边开始判断，包含三个指针，当前值小于标杆值，小边界，活动指针各加1，并替换
			if (arr[l] < arr[r]) {
				swap(arr, ++less, l++);
			// 当前值大于标杆值，大边界减1，活动指针不变，并替换
			} else if (arr[l] > arr[r]) {
				swap(arr, --more, l);
			} else {
			// 当前值等于标杆值，活动指针+1
				l++;
			}
		}
		// l == more, 活动指针与大边界相撞，替换
		swap(arr, more, r);
		// 返回中间值的区间
		return new int[] { less + 1, more };
	}

	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}

	// for test
	public static void comparator(int[] arr) {
		Arrays.sort(arr);
	}

	// for test
	public static int[] generateRandomArray(int maxSize, int maxValue) {
		int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) ((maxValue + 1) * Math.random()) - (int) (maxValue * Math.random());
		}
		return arr;
	}

	// for test
	public static int[] copyArray(int[] arr) {
		if (arr == null) {
			return null;
		}
		int[] res = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			res[i] = arr[i];
		}
		return res;
	}

	// for test
	public static boolean isEqual(int[] arr1, int[] arr2) {
		if ((arr1 == null && arr2 != null) || (arr1 != null && arr2 == null)) {
			return false;
		}
		if (arr1 == null && arr2 == null) {
			return true;
		}
		if (arr1.length != arr2.length) {
			return false;
		}
		for (int i = 0; i < arr1.length; i++) {
			if (arr1[i] != arr2[i]) {
				return false;
			}
		}
		return true;
	}

	// for test
	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i < arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	// for test
	public static void main(String[] args) {
		int testTime = 500000;
		int maxSize = 100;
		int maxValue = 100;
		boolean succeed = true;
		for (int i = 0; i < testTime; i++) {
			int[] arr1 = generateRandomArray(maxSize, maxValue);
			int[] arr2 = copyArray(arr1);
			quickSort(arr1);
			comparator(arr2);
			if (!isEqual(arr1, arr2)) {
				succeed = false;
				printArray(arr1);
				printArray(arr2);
				break;
			}
		}
		System.out.println(succeed ? "Nice!" : "Fucking fucked!");

		int[] arr = generateRandomArray(maxSize, maxValue);
		printArray(arr);
		quickSort(arr);
		printArray(arr);

	}

}
