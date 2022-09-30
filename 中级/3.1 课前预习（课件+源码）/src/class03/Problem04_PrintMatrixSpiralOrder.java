package class03;

/**
 * 用螺旋的方式打印矩阵，比如如下的矩阵
 * 0  1  2  3
 * 4  5  6  7
 * 8  9 10 11
 * 打印顺序为：0 1 2 3 7 11 10 9 8 4 5 6
 */
public class Problem04_PrintMatrixSpiralOrder {

	// 思路：宏观考虑，增加螺旋打印的方法，入参为左上角位置和右下角位置
	// 当出现交错时结束循环
	public static void spiralOrderPrint(int[][] matrix) {
		int tR = 0;
		int tC = 0;
		int dR = matrix.length - 1;
		int dC = matrix[0].length - 1;
		// 打印条件
		while (tR <= dR && tC <= dC) {
			printEdge(matrix, tR++, tC++, dR--, dC--);
		}
	}

	public static void printEdge(int[][] m, int tR, int tC, int dR, int dC) {
		if (tR == dR) {
			// 如果只有一行时，直接for循环打印
			for (int i = tC; i <= dC; i++) {
				System.out.print(m[tR][i] + " ");
			}
		} else if (tC == dC) {
			// 只有一列的情况
			for (int i = tR; i <= dR; i++) {
				System.out.print(m[i][tC] + " ");
			}
		} else {
			int curC = tC;
			int curR = tR;
			// 其他情况先向右打印行，再向下打印列，直到回到起始位置
			while (curC != dC) {
				System.out.print(m[tR][curC] + " ");
				curC++;
			}
			while (curR != dR) {
				System.out.print(m[curR][dC] + " ");
				curR++;
			}
			while (curC != tC) {
				System.out.print(m[dR][curC] + " ");
				curC--;
			}
			while (curR != tR) {
				System.out.print(m[curR][tC] + " ");
				curR--;
			}
		}
	}

	public static void main(String[] args) {
		int[][] matrix = {
				{ 1, 2, 3, 4 },
				{ 5, 6, 7, 8 },
				{ 9, 10, 11, 12 },
				{ 13, 14, 15, 16 } };
		spiralOrderPrint(matrix);

	}

}
