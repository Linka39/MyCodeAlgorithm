
package behaviour.interpreter;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 当我们使用JDBC时，执行的SQL语句虽然是字符串，但最终需要数据库服务器的SQL解释器来把SQL“翻译”成数据库服务器能执行的代码，
 这个执行引擎也非常复杂，但对于使用者来说，仅仅需要写出SQL字符串即可。
 练习
 请实现一个简单的解释器，它可以以SLF4J的日志格式输出字符串：
 */
public class Main {

	public static void main(String[] args) {
		log("[{}] start {} at {}...", LocalTime.now().withNano(0), "engine", LocalDate.now());
	}

	static void log(String format, Object... args) {
		int len = format.length();
		int argIndex = 0;
		StringBuilder sb = new StringBuilder(len + 20);
		char last = '\0';
		for (int i = 0; i < len; i++) {
			char ch = format.charAt(i);
			if (ch == '}') {
				if (last == '{') {
					sb.deleteCharAt(sb.length() - 1);
					sb.append(args[argIndex++]);
				} else {
					sb.append(ch);
				}
			} else {
				sb.append(ch);
			}
			last = ch;
		}
		System.out.println(sb.toString());
	}

}
