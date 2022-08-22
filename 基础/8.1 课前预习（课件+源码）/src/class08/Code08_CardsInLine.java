package class08;

/**
 * 给定一个整型数组arr，代表数值不同的纸牌排成一条线。
 * 玩家A和玩家B依次拿走每张纸 牌，规定玩家A先拿，玩家B后拿，但是每个玩家每次只能拿走最左或最右的纸牌，玩家A 和玩家B都绝顶聪明。
 * 请返回最后获胜者的分数。
 * 【举例】 arr=[1,2,100,4]。 开始时，玩家A只能拿走1或4。如果开始时玩家A拿走1，则排列变为[2,100,4]，
 * 接下来玩家 B可以拿走2或4，然后继续轮到玩家A... 如果开始时玩家A拿走4，则排列变为[1,2,100]，接下来玩家B可以拿走1或100，然后继续轮到玩家A...
 *
 * 玩家A作为绝顶聪明的人不会先拿4，因为拿4之后，玩家B将拿走100。所以玩家A会先拿1， 让排列变为[2,100,4]，接下来玩家B不管怎么选，100都会被玩家 A拿走。
 * 玩家A会获胜，分数为101。所以返回101。 arr=[1,100,2]。
 * 开始时，玩家A不管拿1还是2，玩家B作为绝顶聪明的人，都会把100拿走。玩家B会获胜， 分数为100。所以返回100。
 */
public class Code08_CardsInLine {

	// 策略：分为先手和后手的函数，先手拿最大的，后手拿最小的，返回最大值
	public static int win1(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		return Math.max(f(arr, 0, arr.length - 1), s(arr, 0, arr.length - 1));
	}

	public static int f(int[] arr, int i, int j) {
		// 当只剩下一个时，递归结束
		if (i == j) {
			return arr[i];
		}
		// 递归获取先手拿左边和右边后，分别对应的最大值
		return Math.max(arr[i] + s(arr, i + 1, j), arr[j] + s(arr, i, j - 1));
	}

	public static int s(int[] arr, int i, int j) {
		if (i == j) {
			return 0;
		}
		// 递归获取先手拿完左边，右边后，对应的最小值
		return Math.min(f(arr, i + 1, j), f(arr, i, j - 1));
	}

	public static int win2(int[] arr) {
		if (arr == null || arr.length == 0) {
			return 0;
		}
		int[][] f = new int[arr.length][arr.length];
		int[][] s = new int[arr.length][arr.length];
		for (int j = 0; j < arr.length; j++) {
			f[j][j] = arr[j];
			for (int i = j - 1; i >= 0; i--) {
				// f[i][j] i 代表从最左边拿，j 代表拿最右边的
				f[i][j] = Math.max(arr[i] + s[i + 1][j], arr[j] + s[i][j - 1]);
				s[i][j] = Math.min(f[i + 1][j], f[i][j - 1]);
			}
		}
		return Math.max(f[0][arr.length - 1], s[0][arr.length - 1]);
	}

	public static void main(String[] args) {
		int[] arr = { 1, 9, 1 };
		System.out.println(win1(arr));
		System.out.println(win2(arr));

	}

}
