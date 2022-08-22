package class08;

import java.util.ArrayList;
import java.util.List;

/**
 * 打印一个字符串的全部子序列，包括空字符串
 *
 */
public class Code02_PrintAllSubsquences {

	public static void printAllSubsquence(String str) {
		char[] chs = str.toCharArray();
		process(chs, 0);
	}

	// 策略：子序列就是通过两次递归遍历，第二次遍历为把当前下标置为0，如果下标移动到字符长度时，打印
	public static void process(char[] chs, int i) {
		// 如果下标移动到字符长度时，打印
		if (i == chs.length) {
			System.out.println(String.valueOf(chs));
			return;
		}
		process(chs, i + 1);
		char tmp = chs[i];	// 依靠系统栈保存当前索引上的值
		chs[i] = 0; 		// 当前索引为0，则不会去打印
		process(chs, i + 1);
		chs[i] = tmp;		// 将保存的值重新赋值，进行还原
	}

	public static void function(String str) {
		char[] chs = str.toCharArray();
		process(chs, 0, new ArrayList<Character>());
	}

	public static void process(char[] chs, int i, List<Character> res) {
		if(i == chs.length) {
			printList(res);
		}
		List<Character> resKeep = copyList(res);
		resKeep.add(chs[i]);
		process(chs, i+1, resKeep);
		List<Character> resNoInclude = copyList(res);
		process(chs, i+1, resNoInclude);
	}

	public static void printList(List<Character> res) {
		// ...;
	}

	public static List<Character> copyList(List<Character> list){
		return null;
	}


	public static void main(String[] args) {
		String test = "abc";
		printAllSubsquence(test);
	}

}
