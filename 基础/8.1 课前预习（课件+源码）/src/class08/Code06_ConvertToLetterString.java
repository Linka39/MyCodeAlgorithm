package class08;

/**
 * 规定1和A对应、2和B对应、3和C对应...
 * 那么一个数字字符串比如"111"，就可以转化为"AAA"、"KA"和"AK"。
 * 给定一个只有数字字符组成的字符串str，返回有多少种转化结果。
 */
public class Code06_ConvertToLetterString {

    public static int number(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return process(str.toCharArray(), 0, "");
    }

    // 对策：将字符串转换分为 '0','1','2' 三种情况，
    // 如果当前字符为'0',代表转换失败
    // 为'1'，可以有跳一字符的情况
    // 为'2'且下一字符为'0'到’6‘时，可以有跳一字符的情况
    public static int process(char[] chs, int i, String str) {
        if (i == chs.length) {
            System.out.println(str);
            return 1;
        }
        if (chs[i] == '0') {
            return 0;
        }
        if (chs[i] == '1') {
            str += chs[i] + "  ";
            int res = process(chs, i + 1, str);
            if (i + 1 < chs.length) {
                // String 在拼接时会产生一个新的实例，先将之前的拼接去除掉
                str = str.substring(0,str.lastIndexOf(chs[i]));
                str += chs[i] + "" + chs[i + 1] + "  ";
                // 结果累计加1
                res += process(chs, i + 2, str);
            }
            return res;
        }
        if (chs[i] == '2') {
            str += chs[i] + "  ";
            int res = process(chs, i + 1, str);
            if (i + 1 < chs.length && (chs[i + 1] >= '0' && chs[i + 1] <= '6')) {
                str = str.substring(0,str.lastIndexOf(chs[i]));
                str += chs[i] + "" + chs[i + 1] + "  ";
                res += process(chs, i + 2, str);
            }
            return res;
        }
        return process(chs, i + 1, str);
    }

    public static void main(String[] args) {
        System.out.println(number("11111"));
    }

}
