package class04;

import java.util.HashMap;
import java.util.Map.Entry;

/**
 * 给定一个数组arr，已知其中所有的值都是非负的，将这个数组看作一个容器，
 * 请返回容器能装多少水 比如，arr = {3，1，2，5，2，4}，根据值画出的直方图就是容器形状，
 * 该容器可以装下5格水 再比如，arr = {4，5，1，3，2}，该容器可以装下2格
 */
public class Problem05_WaterProblem {

	// 思路：暴力求解，求出每个桶的左边最大高度，右边最大高度，求出两者的最大高度max
	// 并用max - 当前计算的桶高度得出
	public static int getWater1(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int value = 0;
		for (int i = 1; i < arr.length - 1; i++) {
			int leftMax = 0;
			int rightMax = 0;
			for (int l = 0; l < i; l++) {
				leftMax = Math.max(arr[l], leftMax);
			}
			for (int r = i + 1; r < arr.length; r++) {
				rightMax = Math.max(arr[r], rightMax);
			}
			value += Math.max(0, Math.min(leftMax, rightMax) - arr[i]);
		}
		return value;
	}

	// 思路：采取预计算的方式。求出从左数的依次最大高度数组leftMaxs，从右数的rightMaxs
	// 遍历水桶，求当前桶最左和最右的值哪个最大，（最大值 - 当前桶高度）得到当前桶的最大高度
	public static int getWater2(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length - 2;
		int[] leftMaxs = new int[n];
		leftMaxs[0] = arr[0];
		for (int i = 1; i < n; i++) {
			leftMaxs[i] = Math.max(leftMaxs[i - 1], arr[i]);
		}
		int[] rightMaxs = new int[n];
		rightMaxs[n - 1] = arr[n + 1];
		for (int i = n - 2; i >= 0; i--) {
			rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i + 2]);
		}
		int value = 0;
		for (int i = 1; i <= n; i++) {
			value += Math.max(0, Math.min(leftMaxs[i - 1], rightMaxs[i - 1]) - arr[i]);
		}
		return value;
	}

	public static int getWater3(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int n = arr.length - 2;
		int[] rightMaxs = new int[n];
		rightMaxs[n - 1] = arr[n + 1];
		for (int i = n - 2; i >= 0; i--) {
			rightMaxs[i] = Math.max(rightMaxs[i + 1], arr[i + 2]);
		}
		int leftMax = arr[0];
		int value = 0;
		for (int i = 1; i <= n; i++) {
			value += Math.max(0, Math.min(leftMax, rightMaxs[i - 1]) - arr[i]);
			leftMax = Math.max(leftMax, arr[i]);
		}
		return value;
	}

	public static int getWater4(int[] arr) {
		if (arr == null || arr.length < 3) {
			return 0;
		}
		int value = 0;
		int leftMax = arr[0];
		int rightMax = arr[arr.length - 1];
		int l = 1;
		int r = arr.length - 2;
		while (l <= r) {
			if (leftMax <= rightMax) {
				value += Math.max(0, leftMax - arr[l]);
				leftMax = Math.max(leftMax, arr[l++]);
			} else {
				value += Math.max(0, rightMax - arr[r]);
				rightMax = Math.max(rightMax, arr[r--]);
			}
		}
		return value;
	}

	public static int[] generateRandomArray() {
		int[] arr = new int[(int) (Math.random() * 98) + 2];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * 200) + 2;
		}
		return arr;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 1000000; i++) {
			int[] arr = generateRandomArray();
			int r1 = getWater1(arr);
			int r2 = getWater2(arr);
			int r3 = getWater3(arr);
			int r4 = getWater4(arr);
			if (r1 != r2 || r3 != r4 || r1 != r3) {
				System.out.println("What a fucking day! fuck that! man!");
			}
		}

		HashMap<String,String> map = new HashMap<String,String>();

        for(Entry<String,String> entry : map.entrySet()){
            System.out.println(entry.getKey()+" , "+ entry.getValue());
        }

	}

}
