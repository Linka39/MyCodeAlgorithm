package class02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * 给定一个数组arr，求差值为k的去重数字对。
 */
public class Problem01_SubvalueEqualK {

    // 思路：用hashSet去重，并将去重后的数字对放到 List<List> 的结构里
    public static List<List> allPair(int[] arr, int k) {
        // 将数组放入set里去重
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < arr.length; i++) {
            set.add(arr[i]);
        }
        // 将数字对加入到结果集里
        List<List> res = new ArrayList<>();
        for (Integer cur : set) {
            if (set.contains(cur + k)) {
                res.add(Arrays.asList(cur, cur + k));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        int[] arr = {1, 3, 6, 7, 11, 4};
        // list 可以不用声明泛型，默认会采取强转的方式来获取数据
        List<List> pairs = allPair(arr, 3);
        pairs.stream().forEach(o -> {
            o.forEach(j -> System.out.print(j + "  "));
            System.out.println();
        });
    }
}
