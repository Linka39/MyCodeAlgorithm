package class01;

/**
 * 给定一个函数f，可以1～5的数字等概率返回一个。请加工出1～7的数字等概率 返回一个的函数g。
 * 给定一个函数f，可以a～b的数字等概率返回一个。请加工出c～d的数字等概率 返回一个的函数g。
 * 给定一个函数f，以p概率返回0，以1-p概率返回1。请加工出等概率返回0和1的 函数g，用这个函数随机产生1到6的数字。
 */
public class Problem05_Rand5ToRand7 {

	public static int rand1To5() {
		return (int) (Math.random() * 5) + 1;
	}

	/**
	 * 思路
	 * 假设原始函数为rand1to5()，它能够随机的产生1，2，3，4，5这5个数字。
	 * 那么rand1to5() - 1能够随机产生0，1，2，3，4这5个数字。
	 * 那么(rand1to5() - 1) * 5能够随机产生0， 5， 10， 15， 20这5个数字。
	 * 那么(rand1to5() - 1) * 5 + rand1to5() - 1能够随机产生0到24之间的所有数字，假设用t来保存这个数值。
	 * （注意，因为分别两次使用了rand1to5()这个函数，实际上这两次产生的值是没有关联的）
	 * 0~20代表着对于7取余时，0-6的区间都是随机出现的， 如果t大于20的话，那么重新计算一次(rand1to5() - 1) * 5 + rand1to5() - 1，
	 * 一直到t小于等于20，由于大于20的概率是随机的，所以可以认为随机产生了0到20之间的21个数字。
	 * 这样t % 7 + 1就可以随机产生1到7之间的数字了。
	 * @return
	 */
	public static int rand1To7() {
		int num = 0;
		do {
			// (rand1to5() - 1) * 5能够随机产生0， 5， 10， 15， 20这5个数字。
			// (rand1to5() - 1) * 5 + rand1to5() - 1能够随机产生0到24之间的所有数字，假设用t来保存这个数值。
			num = (rand1To5() - 1) * 5 + rand1To5() - 1;
		// 0~20代表着对于7取余的区域几率相等
		} while (num > 20);
		return num % 7 + 1;
	}

	/**
	 * 题目1
	 * 有个函数以p概率返回0，1-p的概率返回1，用这个函数随机产生1到6的数字。
	 *
	 * 思路
	 * 假设这个函数是rand0to1()，调用它两次，第一次是0，第二次是1（即01）的概率是p * （1 - p），第一次是1，
	 * 第二次是0（即10）的概率是（1 - p）* p,他们的概率是一样的，即可以随机产生01和10，此时只要取第一次（或者取第二次）产生的数就可以随机产生0和1了，假设为rand0and1();
	 *
	 *  同上面的做法，rand0and1() * 2 可以随机产生0和2；
	 * 那么rand0and1() * 2 + rand0and1()可以随机产生0，1，2，3，记为rand0to3().
	 * 那么rand0to3() * 2可以随机产生0，2，4，6；
	 * 那么rand0to3() * 2 + rand0and1()，可以随机产生0，1，2，3，4，5，6，7，记为t
	 *
	 * 如果t>5就重新计算rand0to3() * 2 + rand0and1()，一直到t <= 5;
	 * 那么t % 6 + 1就能随机产生1到6的数字了。
	 * @return
	 */
	public static int rand01p() {
		// you can change p to what you like, but it must be (0,1)
		double p = 0.83;
		return Math.random() < p ? 0 : 1;
	}

	// 计算等概率返回0 或 1的函数
	public static int rand01() {
		int num;
		do {
			num = rand01p();
		} while (num == rand01p());
		return num;
	}

	// rand0and1() * 2 可以随机产生0和2，rand01() * 2 + rand01()就是随机 0~3
	public static int rand0To3() {
		return rand01() * 2 + rand01();
	}

	//	 rand0to3() * 2 可以随机产生0，2，4，6；
	//	 rand0to3() * 2 + rand0and1()，可以随机产生0，1，2，3，4，5，6，7，记为t
	public static int rand1To6() {
		int num = 0;
		do {
			num = rand0To3() * 2 + rand01();
			// 如果t>5就重新计算rand0to3() * 2 + rand0and1()，一直到t <= 5;
		} while (num > 5);
		return num % 6 + 1;
	}

	public static int rand1ToM(int m) {
		return (int) (Math.random() * m) + 1;
	}

	public static int rand1ToN(int n, int m) {
		int[] nMSys = getMSysNum(n - 1, m);
		int[] randNum = getRanMSysNumLessN(nMSys, m);
		return getNumFromMSysNum(randNum, m) + 1;
	}

	public static int[] getMSysNum(int value, int m) {
		int[] res = new int[32];
		int index = res.length - 1;
		while (value != 0) {
			res[index--] = value % m;
			value = value / m;
		}
		return res;
	}

	// �ȸ����������һ��0~nMsys��Χ�ϵ�����ֻ������m���Ʊ��ġ�
	public static int[] getRanMSysNumLessN(int[] nMSys, int m) {
		int[] res = new int[nMSys.length];
		int start = 0;
		while (nMSys[start] == 0) {
			start++;
		}
		int index = start;
		boolean lastEqual = true;
		while (index != nMSys.length) {
			res[index] = rand1ToM(m) - 1;
			if (lastEqual) {
				if (res[index] > nMSys[index]) {
					index = start;
					lastEqual = true;
					continue;
				} else {
					lastEqual = res[index] == nMSys[index];
				}
			}
			index++;
		}
		return res;
	}

	// ��m���Ƶ���ת��10����
	public static int getNumFromMSysNum(int[] mSysNum, int m) {
		int res = 0;
		for (int i = 0; i != mSysNum.length; i++) {
			res = res * m + mSysNum[i];
		}
		return res;
	}

	public static void printCountArray(int[] countArr) {
		for (int i = 0; i != countArr.length; i++) {
			System.out.println(i + " appears " + countArr[i] + " times");
		}
	}

	public static void main(String[] args) {
		int testTimes = 1000000;
		int[] countArr1 = new int[8];
		for (int i = 0; i != testTimes; i++) {
			countArr1[rand1To7()]++;
		}
		printCountArray(countArr1);

		System.out.println("=====================");

		int[] countArr2 = new int[7];
		for (int i = 0; i != testTimes; i++) {
			countArr2[rand1To6()]++;
		}
		printCountArray(countArr2);

		System.out.println("=====================");

		int n = 17;
		int m = 3;
		int[] countArr3 = new int[n + 1];
		for (int i = 0; i != 2000000; i++) {
			countArr3[rand1ToN(n, m)]++;
		}
		printCountArray(countArr3);

		System.out.println("=====================");

	}

}
