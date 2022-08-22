package class01;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/**
 * 并查集结构的详解和实现
 */
public class Code04_UnionFind {

	// 定义一个关于泛型的并查集
	public static class Element<V> {
		public V value;

		public Element(V value) {
			this.value = value;
		}

	}

	public static class UnionFindSet<V> {
		// 分别定义元素map，父节点map，和数量的map
		public HashMap<V, Element<V>> elementMap;
		public HashMap<Element<V>, Element<V>> fatherMap;
		public HashMap<Element<V>, Integer> rankMap;

		// 初始化并查集
		public UnionFindSet(List<V> list) {
			elementMap = new HashMap<>();
			fatherMap = new HashMap<>();
			rankMap = new HashMap<>();
			for (V value : list) {
				// 将泛型中的元素填充到元素数组中
				Element<V> element = new Element<V>(value);
				elementMap.put(value, element);
				fatherMap.put(element, element);
				rankMap.put(element, 1);
			}
		}

		// 查找element的祖先节点
		private Element<V> findHead(Element<V> element) {
			Stack<Element<V>> path = new Stack<>();
			// 将element赋值为祖先节点，父节点与当前节点相等的为祖先节点
			while (element != fatherMap.get(element)) {
				path.push(element);
				element = fatherMap.get(element);
			}
			// 在查找的过程中将路径塞到栈里，依次把路径里的节点设置为祖先节点
			while (!path.isEmpty()) {
				fatherMap.put(path.pop(), element);
			}
			return element;
		}

		// 判断是否在一个并查集里
		public boolean isSameSet(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				return findHead(elementMap.get(a)) == findHead(elementMap.get(b));
			}
			return false;
		}

		// 融合两个两个并查集，将分数大的的作为父集，并更新相关的数量集
		public void union(V a, V b) {
			if (elementMap.containsKey(a) && elementMap.containsKey(b)) {
				Element<V> aF = findHead(elementMap.get(a));
				Element<V> bF = findHead(elementMap.get(b));
				if (aF != bF) {
					Element<V> big = rankMap.get(aF) >= rankMap.get(bF) ? aF : bF;
					Element<V> small = big == aF ? bF : aF;
					fatherMap.put(small, big);
					rankMap.put(big, rankMap.get(aF) + rankMap.get(bF));
					rankMap.remove(small);
				}
			}
		}

	}

	public static void main(String[] args) {
		testUnion();
	}

	public static void testUnion(){
		// Integer类型有个常量池，当==比较或使用这个范围内，就可以直接使用缓存池中的数据，从而节省内存。Integer.valueOf对于-128-127之间的数字，始终返回相同的实例
		List<Integer> integers = Arrays.asList(new Integer[]{2, 3, 114, 1});
		UnionFind<Integer> union = new UnionFind<>(integers);
		union.union(2, 114);
		System.out.println(union.isSameUnion(2,3) + " " + union.isSameUnion(2, 4));

		List<Integer> integers2 = Arrays.asList(new Integer[]{2, 3, 4, 1});
		UnionFind<Integer> union2 = new UnionFind<>(integers);
		union.union(2, 4);
		System.out.println(union.isSameUnion(2,3) + " " + union.isSameUnion(2, 4));
	}

	static class UnionFind<V> {
		public HashMap<V, V> fatherMap;
		public HashMap<V, Integer> rankMap;

		public UnionFind(List<V> list){
			fatherMap = new HashMap<>();
			rankMap = new HashMap<>();
			for (V v : list) {
				fatherMap.put(v, v);
				rankMap.put(v, 1);
			}
		}

		public V getFather(V v) {
			V f = fatherMap.get(v);
			Stack<V> stack = new Stack<>();
			while (f != v) {
				stack.push(v);
				f = fatherMap.get(v);
				v = f;
			}
			while (!stack.isEmpty()) {
				fatherMap.put(stack.peek(), f);
				stack.pop();
			}
			return f;
		}

		public boolean isSameUnion(V v1, V v2){
			V f1 = getFather(v1);
			V f2 = getFather(v2);
			return f1 == f2;
		}

		public void union(V v1, V v2){
			V f1 = getFather(v1);
			V f2 = getFather(v2);
			if (f1 != f2) {
				int r1 = rankMap.get(f1);
				int r2 = rankMap.get(f2);
				if (r1 < r2) {
					fatherMap.put(f1, f2);
					rankMap.put(f2, r1 + r2);
				}else {
					fatherMap.put(f2, f1);
					rankMap.put(f1, r1 + r2);
				}
			}
		}
	}

}
