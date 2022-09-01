package class01;

/**
 * 小虎去附近的商店买苹果，奸诈的商贩使用了捆绑交易，只提供6个每袋和8个每袋的包装包装不可拆分。
 * 可是小虎现在只想购买恰好n个苹果，小虎想购买尽 量少的袋数方便携带。如果不能购买恰好n个苹果，小虎将不会购买。
 *
 * 输入一个 整数n，表示小虎想购买的个苹果，返回最小使用多少袋子。如果无论如何都不能正好装下，返回-1。
 */
public class Problem02_AppleMinBags {

	// 思路：贪心算法，首先对8取余，不能整除的话，在8和6的最小公倍数范围中看是否能被6整除
	// 最终返回 bag6 bag8，不能整除返回-1
	public static int minBags(int apple) {
		if (apple < 0) {
			return -1;
		}
		int bag6 = -1;
		int bag8 = apple / 8;
		int rest = apple - 8 * bag8;
		while (bag8 >= 0 && rest < 24) {
			int restUse6 = minBagBase6(rest);
			if (restUse6 != -1) {
				bag6 = restUse6;
				break;
			}
			// rest 逐渐变大，看能否被6整除
			rest = apple - 8 * (--bag8);
		}
		return bag6 == -1 ? -1 : bag6 + bag8;
	}

	public static int minBagBase6(int rest) {
		return rest % 6 == 0 ? (rest / 6) : -1;
	}

	// 打表法，找规律根据入参x 抽象出一个一个公式 f(x) 来得到结果
	public static int minBagAwesome(int apple) {
		if ((apple & 1) != 0) {
			return -1;
		}
		if (apple < 18) {
			return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
					: (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
		}
		return (apple - 18) / 8 + 3;
	}

	public static void main(String[] args) {
		int max = Integer.MAX_VALUE;
		int testTime = 100000000;
		for (int test = 0; test < testTime; test++) {
			int apple = (int) (Math.random() * max);
			if (minBags(apple) != minBagAwesome(apple)) {
				System.out.println("error");
			}
		}

	}

}
