package class05;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * 为了找到自己满意的工作，牛牛收集了每种工作的难度和报酬。
 * 牛牛选工作的标准是在难度不超过自身能力 值的情况下，牛牛选择报酬最高的工作。
 * 在牛牛选定了自己的工作后，牛牛的小伙伴们来找牛牛帮忙选工作， 牛牛依然使用自己的标准来帮助小伙伴们。
 * 牛牛的小伙伴太多了，于是他只好把这个任务交给了你。
 *
 * class Job {
 *     public int money;// 该工作的报酬
 *     public int hard; // 该工作的难度
 *     public Job(int money, int hard) {
 *     		this.money = money;
 *     		this.hard = hard;
 *     }
 *  }
 * 给定一个Job类型的数组jobarr，表示所有的工作。给定一个int类型的数组arr，表示所有小伙伴的能力。
 * 返回int类型的数组，表示每一个小伙伴按照牛牛的标准选工作后所能获得的报酬。
 */
public class Problem07_ChooseWork {

	public static class Job {
		public int money;
		public int hard;

		public Job(int money, int hard) {
			this.money = money;
			this.hard = hard;
		}
	}

	public static class JobComparator implements Comparator<Job> {
		@Override
		public int compare(Job o1, Job o2) {
			return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
		}
	}

	// 思路：首先将工作数组按照难度进行从小到大排序，难度一样则按照金钱从大到小排序，
	// 在 TreeMap 里存入能力匹配的金钱工作，最后直接从 TreeMap 里获取和能力相匹配的报酬工作。
	public static int[] getMoneys(Job[] job, int[] ability) {
		Arrays.sort(job, new JobComparator());
		TreeMap<Integer, Integer> map = new TreeMap<>();
		map.put(job[0].hard, job[0].money);
		Job pre = job[0];
		for (int i = 1; i < job.length; i++) {
			// 如果难度不一样时，难度小的工作金钱大于难度的，则当前工作难度存入较大的金钱
			if (job[i].hard != pre.hard && job[i].money > pre.money) {
				pre = job[i];
				map.put(pre.hard, pre.money);
			}
		}
		// 遍历职员能力数组，通过 ceilingKey 获取大于等于当前能力的最大金钱
		int[] ans = new int[ability.length];
		for (int i = 0; i < ability.length; i++) {
			Integer key = map.ceilingKey(ability[i]);
			ans[i] = key != null ? map.get(key) : 0;
		}
		return ans;
	}

	public static void main(String[] args) {
		TreeMap<Integer, Integer> map = new TreeMap<>();
		map.put(6,3);
		Integer key = map.ceilingKey(5);
		// ceilingKey(K key)	返回大于或等于给定键的最小键，如果没有这样的键，则null
		// floorKey(K key)	返回小于或等于给定键的最大键，如果没有这样的键，则null
		int test1 = key != null ? map.get(key) : 0;
		System.out.println(test1);
		int test2 = map.get(map.ceilingKey(7));
		System.out.println(test2);
	}

}
