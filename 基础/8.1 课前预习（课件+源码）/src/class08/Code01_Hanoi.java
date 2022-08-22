package class08;

/**
 * 汉诺塔问题
 * 打印n层汉诺塔从最左边移动到最右边的全部过程
 */
public class Code01_Hanoi {

	public static void hanoi(int n) {
		if (n > 0) {
			func(n, n, "left", "mid", "right");
		}
	}

	// 参数分别为 剩下的，当前移动的圆盘，from柱，暂存柱，to柱
	// 将圆盘从from移到to，规定移动时大盘只能压到小盘后面
	public static void func(int rest, int down, String from, String help, String to) {
		if (rest == 1) {
			System.out.println("move " + down + " from " + from + " to " + to);
		} else {
			// 局部规划，前面的圆盘依次移到暂存柱上
			func(rest - 1, down - 1, from, to, help);
			// 最后一个圆盘移到to柱
			func(1, down, from, help, to);
			// 暂存柱上依次移到to柱上
			func(rest - 1, down - 1, help, from, to);
		}
	}

	public static void main(String[] args) {
		int n = 3;
		hanoi(n);
	}

}
