package class05;

import java.util.ArrayList;
import java.util.List;

/**
 * 派对的最大快乐值  
 * 员工信息的定义如下:
 * class Employee {     
 * 		public int happy; 		// 这名员工可以带来的快乐值   
 * 	  List<Employee> subordinates; // 这名员工有哪些直接下级
 * 	}  
 * 	公司的每个员工都符合 Employee 类的描述。整个公司的人员结构可以看作是一棵标准的、没有环的多叉树。
 * 	树的头节点是公司唯一的老板。除老板之外的每个员工都有唯一的直接上级。 
 * 	叶节点是没有 任何下属的基层员工(subordinates列表为空)，除基层员工外，每个员工都有一个或多个直接下级。  
 * 	这个公司现在要办party，你可以决定哪些员工来，哪些员工不来。但是要遵循如下规则。 
 * 		1.如果某个员工来了，那么这个员工的所有直接下级都不能来
 * 		2.派对的整体快乐值是所有到场员工快乐值的累加
 * 		3.你的目标是让派对的整体快乐值尽量大  
 * 	给定一棵多叉树的头节点boss，请返回派对的最大快乐值。
 */
public class Code03_MaxHappy {

	public static int maxHappy(int[][] matrix) {
		int[][] dp = new int[matrix.length][2];			// 0: 当前员工参加的最大快乐值，1:不参加的最大快乐值
		boolean[] visited = new boolean[matrix.length];	// 是否参加party
		int root = 0;
		for (int i = 0; i < matrix.length; i++) {
			if (i == matrix[i][0]) {
				root = i;
			}
		}
		process(matrix, dp, visited, root);
		return Math.max(dp[root][0], dp[root][1]);
	}

	public static void process(int[][] matrix, int[][] dp, boolean[] visited, int root) {
		visited[root] = true;
		dp[root][1] = matrix[root][1];
		for (int i = 0; i < matrix.length; i++) {
			if (matrix[i][0] == root && !visited[i]) {
				process(matrix, dp, visited, i);
				dp[root][1] += dp[i][0];
				dp[root][0] += Math.max(dp[i][1], dp[i][0]);
			}
		}
	}

	// 定义泛型员工节点
	public static class EmpNode<T>{
		public T value;
		EmpNode left;
		EmpNode right;
		public EmpNode(T value){
			this.value = value;
		}
	}

	// 定义员工节点
	public static class Employee {
		public int happy;	            // 这名员工可以带来的快乐值   
		List<Employee> subordinates;    // 这名员工有哪些直接下级
		public Employee(int happy){
			this.happy = happy;
		}
	}

	// 定义返回结构
	public static class ResultType {
		int ComeHappy;      // 此员工参加的总幸福值
		int NotComHappy;    // 不参加的总幸福值
		public ResultType(int c, int n){
			ComeHappy = c;
			NotComHappy = n;
		}
	}

	public static ResultType getMaxHappiness(Employee head){
		// 如果是下层员工的话，参加的话能获得最大幸福值，只能参加
		if (head.subordinates == null){
			return new ResultType(head.happy, 0);
		}
		int come = head.happy;
		int notCome = 0;
		for (Employee subordinate : head.subordinates) {
			ResultType rst = getMaxHappiness(subordinate);
			come += rst.NotComHappy; // 来的话加上下属不来的快乐值
			notCome += Math.max(rst.ComeHappy, rst.NotComHappy); // 不来的话取下属员工来或不来的最大值
		}
		return new ResultType(come, notCome); // 最后返回结构集进行衔接
	}

	public static void testMaxHappy(){
		// 构建测试数据
		Employee rootEmp = new Employee(4);
		Employee Emp2 = new Employee(2);
		Employee Emp1 = new Employee(1);
		Employee Emp3 = new Employee(3);
		Employee Emp6 = new Employee(6);
		ArrayList<Employee> first_subemps = new ArrayList<>();
		first_subemps.add(Emp2);
		first_subemps.add(Emp6);
		rootEmp.subordinates = first_subemps;

		ArrayList<Employee> second_subemps = new ArrayList<>();
		second_subemps.add(Emp1);
		second_subemps.add(Emp3);
		Emp2.subordinates = second_subemps;

		// 得到最终结果值
		ResultType rst = getMaxHappiness(rootEmp);
		System.out.println(Math.max(rst.ComeHappy, rst.NotComHappy));
	}

	public static void main(String[] args) {
//		int[][] matrix = { { 1, 8 }, { 1, 9 }, { 1, 10 } };
//		System.out.println(maxHappy(matrix));
		testMaxHappy();

	}
}
