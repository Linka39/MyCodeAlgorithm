package class07;

import java.util.Arrays;
import java.util.Comparator;
/**
 * 一些项目要占用一个会议室宣讲，会议室不能同时容纳两个项目的宣讲。
 * 给你每一个项目开始的时间和结束的时间(给你一个数组，里面是一个个具体的项目)，你来安排宣讲的日程，要求会议室进行的宣讲的场次最多。
 * 返回这个最多的宣讲场次。
 */
public class Code04_BestArrange {

	// 每个会议抽离出开始时间，结束时间
	public static class Program {
		public int start;
		public int end;

		public Program(int start, int end) {
			this.start = start;
			this.end = end;
		}
	}

	public static Program[] initData() {
		Program[] programArr = new Program[]{
				new Program(2, 15),
				new Program(3, 14),
				new Program(4, 7),
				new Program(8, 10),
				new Program(11, 15)
		};
		return programArr;
	}

	// 贪心策略，按结束时间排序，将结束时间最早的会议入队，看之后的会议开始时间是否在结束之前，不是的话纳入计算
	public static int bestArrange(Program[] programs, int start) {
		programs = initData();
		Arrays.sort(programs, (o1, o2) -> o1.end - o2.end);
		int result = 0;
		for (int i = 0; i < programs.length; i++) {
			if (start <= programs[i].start) {
				result++;
				start = programs[i].end;
			}
		}
		return result;
	}

	public static void main(String[] args) {
		System.out.println(bestArrange(null, 8));
	}

}
