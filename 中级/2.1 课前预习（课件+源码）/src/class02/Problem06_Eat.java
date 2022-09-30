package class02;

/**
 * 牛牛和羊羊都很喜欢青草。今天他们决定玩青草游戏。 最初有一个装有n份青草的箱子,牛牛和羊羊依次进行,牛牛先开始。
 *
 * 在每个回合中,每个 玩家必须吃一些箱子中的青草,所吃的青草份数必须是4的x次幂,比如1,4,16,64等等。
 * 不能在箱子中吃到有效份数青草的玩家落败。假定牛牛和羊羊都是按照最佳方法进行游戏,请输出胜利者的名字。
 */
public class Problem06_Eat {

	// 思路：最小可吃的数目为1，4，按5取余的话，如果==0，可以理解为前者吃了4个，那么代表是后者赢
	// ==2也代表后者,3为前者赢。4代表可以一次性吃完，前者赢
	public static void printWinner(int n) {
		if (n % 5 == 0 || n % 5 == 2 || n % 5 == 3) {
			System.out.println("yang");
		} else {
			System.out.println("niu");
		}
	}

	public static void main(String[] args) {
		printWinner(8);
	}

}
