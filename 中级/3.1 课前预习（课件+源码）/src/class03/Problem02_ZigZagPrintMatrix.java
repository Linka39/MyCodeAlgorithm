package class03;

/**
 * 用zigzag的方式打印矩阵，比如如下的矩阵
 * 0  1  2  3
 * 4  5  6  7
 * 8  9 10 11
 * 打印顺序为：0 1 4 8 5 2 3 6 9 10 7 11
 */
public class Problem02_ZigZagPrintMatrix {
	// 思路：从宏观上考虑进行打印，首先确定一个打印函数设置起始的打印节点和打印方向
	// 如果当前行没有越界的话进行循环打印，每循环一次更改下起始节点的位置
	public static void printMatrixZigZag(int[][] matrix) {
		int tR = 0;
		int tC = 0;	// t为当前轮起始位置节点
		int dR = 0;
		int dC = 0; // d为当前轮结束节点
		int endR = matrix.length - 1;
		int endC = matrix[0].length - 1;
		boolean fromUp = false;
		while (tR != endR + 1) {
			printLevel(matrix, tR, tC, dR, dC, fromUp);
			// 如果到最大列时，起始行+1，否则起始行不变
			tR = tC == endC ? tR + 1 : tR;
			// 如果没到最大列时，起始列+1
			// 注意：在对变量赋值时注意先后顺序，不能让上方的变量赋值影响下面
			tC = tC == endC ? tC : tC + 1;
			dC = dR == endR ? dC + 1 : dC;
			dR = dR == endR ? dR : dR + 1;
			// 打印方向取反, 开始位置根据上一次的结束位置来确定
			fromUp = !fromUp;
		}
		System.out.println();
	}

	// 线形打印方法，f为true时从上往下打印，[tR++][tC--]
	public static void printLevel(int[][] m, int tR, int tC, int dR, int dC,
			boolean f) {
		if (f) {
			while (tR != dR + 1) {
				System.out.print(m[tR++][tC--] + " ");
			}
		} else {
			// f为false 时从下往上打印，[dR--][dC++]
			while (dR != tR - 1) {
				System.out.print(m[dR--][dC++] + " ");
			}
		}
	}

	public static void main(String[] args) {
		int[][] matrix = {
				{ 1, 2, 3, 4 },
				{ 5, 6, 7, 8 },
				{ 9, 10, 11, 12 } };
		printMatrixZigZag(matrix);

	}

}
