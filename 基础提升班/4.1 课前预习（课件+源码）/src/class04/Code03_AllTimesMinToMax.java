package class04;

import java.util.Stack;

/**
 * 定义：数组中累积和与最小值的乘积，假设叫做指标A。
 * 给定一个数组，请返回子数组中，指标A最大的值。
 */
public class Code03_AllTimesMinToMax {

	public static int max1(int[] arr) {
		int max = Integer.MIN_VALUE;
		for (int i = 0; i < arr.length; i++) {
			for (int j = i; j < arr.length; j++) {
				int minNum = Integer.MAX_VALUE;
				int sum = 0;
				// 在i,j之间进行累积求和，并且求出i,j间的最小值
				for (int k = i; k <= j; k++) {
					sum += arr[k];
					minNum = Math.min(minNum, arr[k]);
				}
				// 每次遍历求出最大的值并返回
				max = Math.max(max, minNum * sum);
			}
		}
		return max;
	}

	// 采取单调栈的方式进行求最大值
	// 思路：求出数组中每个值对应的最大指数(首先看数组两边，有无比他小的值，)
	public static int max2(int[] arr) {
		int size = arr.length;
		int[] sums = new int[size];
		// sums为对应下标的累加和
		sums[0] = arr[0];
		for (int i = 1; i < size; i++) {
			sums[i] = sums[i - 1] + arr[i];
		}
		int max = Integer.MIN_VALUE;
		Stack<Integer> stack = new Stack<Integer>();	// 声明一个单调栈
		for (int i = 0; i < size; i++) {
			// 如果遇到栈中数大于数组当前数时，进行出栈操作(出栈的数值即为最小值)，
			while (!stack.isEmpty() && arr[stack.peek()] >= arr[i]) {
				int j = stack.pop();
				// 如果栈是空的，代表当前范围内无最小值，累加和为首索引累加的。否则累加和需减去之前的一段
				int sum = stack.isEmpty() ? sums[i - 1] : (sums[i - 1] - sums[stack.peek()]);
				// 取所有区间中，指标最大的索引
				max = Math.max(max, sum * arr[j]);
			}
			// 遍历结束后将当前值放入到栈中
			stack.push(i);
		}
		// 处理栈中剩下的元素
		while (!stack.isEmpty()) {
			int j = stack.pop();
			max = Math.max(max, (stack.isEmpty() ? sums[size - 1] : (sums[size - 1] - sums[stack.peek()])) * arr[j]);
		}
		return max;
	}

	public static int[] gerenareRondomArray() {
		int[] arr = new int[(int) (Math.random() * 20) + 10];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 101);
		}
		return arr;
	}

	public static void main(String[] args) {
		int testTimes = 2000000;
		for (int i = 0; i < testTimes; i++) {
			int[] arr = gerenareRondomArray();
			if (max1(arr) != max2(arr)) {
				System.out.println("FUCK!");
				break;
			}
		}

	}

}
