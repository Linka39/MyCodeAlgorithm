package class02;

import java.util.Stack;

/**
 * 请编写一个程序，对一个栈里的整型数据，按升序进行排序（即排序前，栈里 的数据是无序的，排序后最大元素位于栈顶），
 * 要求最多只能使用一个额外的 栈存放临时数据，但不得将元素复制到别的数据结构中。
 */
public class Problem05_StackSortStack {

	// 思路：单调栈，准备一个help栈来存储排序后的数据，当help栈顶元素小于源栈时，先放到源栈里暂存
	// 直到源栈元素大于help栈时再依次放入
	public static void sortStackByStack(Stack<Integer> stack) {
		Stack<Integer> help = new Stack<Integer>();
		while (!stack.isEmpty()) {
			int cur = stack.pop();
			while (!help.isEmpty() && help.peek() < cur) {
				stack.push(help.pop());
			}
			help.push(cur);
		}
		while (!help.isEmpty()) {
			stack.push(help.pop());
		}
	}

	public static void main(String[] args) {
		Stack<Integer> stack = new Stack<Integer>();
		stack.push(3);
		stack.push(1);
		stack.push(6);
		stack.push(2);
		stack.push(5);
		stack.push(4);
		sortStackByStack(stack);
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());
		System.out.println(stack.pop());

	}

}
