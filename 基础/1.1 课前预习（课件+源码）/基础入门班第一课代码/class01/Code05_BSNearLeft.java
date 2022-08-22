

/**
 * 问题：在一个有序数组中，找>=某个数最左侧的位置
 */
public class Code05_BSNearLeft {

	// 在arr上，找满足>=value的最左位置
	public static int nearestIndex(int[] arr, int value) {
		int L = 0;
		int R = arr.length - 1;
		int index = -1;
		while (L < R) {
			int mid = L + ((R - L) >> 1);
			// 中间位置如果 >= value的话，说明还在左端位置，左端查找
			if (arr[mid] >= value) {
				// 需要预存一下之前相等的一组数最左面的索引
				index = mid;
				R = mid - 1;
			} else {
				L = mid + 1;
			}
		}
		return index;
	}

}
