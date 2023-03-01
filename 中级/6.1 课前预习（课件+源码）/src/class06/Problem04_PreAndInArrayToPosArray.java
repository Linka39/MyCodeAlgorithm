package class06;

import java.util.HashMap;

/**
 * 已知一棵二叉树中没有重复节点，并且给定了这棵树的中序遍历数组和先序遍历 数组，返回后序遍历数组。
 *
 * 比如给定： int[] pre = { 1, 2, 4, 5, 3, 6, 7 };
 * int[] in = { 4, 2, 5, 1, 6, 3, 7 };
 * 返回： {4,5,2,6,7,3,1}
 */
public class Problem04_PreAndInArrayToPosArray {

	// 思路：使用多重递归，先序遍历的头节点放到后续遍历的最后一位，通过头节点在中序遍历中的位置，确定树的左右子树范围
	public static int[] getPosArray(int[] pre, int[] in) {
		if (pre == null || in == null) {
			return null;
		}
		int len = pre.length;
		int[] pos = new int[len];
		// 使用 map 记录中序的索引位置，以此来定位后续遍历的边界，减少计算量
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < len; i++) {
			map.put(in[i], i);
		}
		setPos(pre, 0, len - 1, in, 0, len - 1, pos, len - 1, map);
		return pos;
	}

	// 递归计算出后序遍历中的位置，pi,pj 代表前序遍历数组的范围，ni,nj 代表中序遍历的范围。si 代表后续遍历所填入的位置
	public static int setPos(int[] p, int pi, int pj, int[] n, int ni, int nj, int[] s, int si,
			HashMap<Integer, Integer> map) {
		if (pi > pj) {
			return si;
		}
		// 后续最后一位放入前序遍历的第一个
		s[si--] = p[pi];
		// 得到父节点索引位置
		int i = map.get(p[pi]);
		// 右孩子范围内后序遍历赋值
		si = setPos(p, pj - (nj - i) + 1, pj, n, i + 1, nj, s, si, map);
		// 左孩子范围赋值
		return setPos(p, pi + 1, pi + i - ni, n, ni, i - 1, s, si, map);
	}

	public static void printArray(int[] arr) {
		if (arr == null) {
			return;
		}
		for (int i = 0; i != arr.length; i++) {
			System.out.print(arr[i] + " ");
		}
		System.out.println();
	}

	public static void main(String[] args) {
		int[] pre = { 1, 2, 4, 5, 3, 6, 7 };
		int[] in = { 4, 2, 5, 1, 6, 3, 7 };
		int[] pos = getPosArray(pre, in);
		printArray(pos);

	}
}
