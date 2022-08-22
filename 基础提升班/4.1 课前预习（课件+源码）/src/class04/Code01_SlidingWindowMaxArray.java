package class04;

import java.util.LinkedList;

/**
 * 【题目】
 * 有一个整型数组arr和一个大小为w的窗口从数组的最左边滑到最右边，窗口每次向右边滑一个位置。
 * 例如，数组为[4,3,5,4,3,3,6,7]，窗口大小为3时:
 * [4 3 5]4 3 3 6 7
 * 4[3 5 4]3 3 6 7
 * 4 3[5 4 3]3 6 7
 * 4 3 5[4 3 3]6 7
 * 4 3 5 4[3 3 6]7
 * 4 3 5 4 3[3 6 7]
 * 窗口中最大值为5 窗口中最大值为5 窗口中最大值为5 窗口中最大值为4 窗口中最大值为6
 * 窗口中最大值为7
 * 如果数组长度为n，窗口大小为w，则一共产生n-w+1个窗口的最大值。
 * 请实现一个函数。 输入:整型数组arr，窗口大小为w。
 * 输出:一个长度为n-w+1的数组res，res[i]表示每一种窗口状态下的 以本题为例，结果应该
 * 返回{5,5,5,4,6,7}。
 *
 * 窗口只能右边界或左边界向右滑的情况下，维持窗口内部最大值或者最小值快速更新的结构
 * 窗口内最大值与最小值更新结构的原理与实现
 */
public class Code01_SlidingWindowMaxArray {

	public static int[] getMaxWindow(int[] arr, int w) {
		if (arr == null || w < 1 || arr.length < w) {
			return null;
		}
		// w 为滑块长度，qmax为滑块数组，结构为双向队列，存放的值为数组下标
		LinkedList<Integer> qmax = new LinkedList<Integer>();
		// 结果数组，存放n-w+1个窗口的最大值
		int[] res = new int[arr.length - w + 1];
		int index = 0;
		for (int i = 0; i < arr.length; i++) {
			// 如果滑块不为空且滑块最后一位小于当前数，尾端出队
			while (!qmax.isEmpty() && arr[qmax.peekLast()] <= arr[i]) {
				qmax.pollLast();
			}
			// 将当前值的下标放入尾端
			qmax.addLast(i);
			// 如果滑块过长时，头部出队
			if (qmax.peekFirst() == i - w) {
				qmax.pollFirst();
			}
			// 当下标移动到滑块极限位置时，排在首位的即为滑块区域的最大值
			if (i >= w - 1) {
				res[index++] = arr[qmax.peekFirst()];
			}
		}
		return res;
	}

	// for test
	public static void printArray(int[] arr) {
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] arr = { 4, 3, 5, 4, 3, 3, 6, 7 };
		int w = 3;
		printArray(getMaxWindow(arr, w));

	}

}
