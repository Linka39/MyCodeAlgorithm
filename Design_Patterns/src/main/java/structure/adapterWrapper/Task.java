package structure.adapterWrapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;

public class Task implements Callable<Long>{
    private long num;
    public Task(long num) {
        this.num = num;
    }

    public Long call() throws Exception {
        long r = 0;
        for (int i = 0; i < this.num; i++) {
            r = r + i;
        }
        System.out.println("Result:" + r);
        return r;
    }

    /**
     * Adapter模式可以将一个A接口转换为B接口，使得新的对象符合B接口规范。
     * 编写Adapter实际上就是编写一个实现了B接口，并且内部持有A接口的类：
     *
     * public BAdapter implements B {
     *     private A a;
     *     public BAdapter(A a) {
     *         this.a = a;
     *     }
     *     public void b() {
     *         a.a();
     *     }
     * }
     * 在Adapter内部将B接口的调用“转换”为对A接口的调用。
     *
     * 只有A、B接口均为抽象接口时，才能非常简单地实现Adapter模式。
     *
     * 读后有收获可以支付宝请作者喝咖啡：
     * @param args
     * @throws IOException
     */

    public static void main(String[] args) throws IOException {
        Callable<Long> callable = new Task(123450000L);
        Thread thread = new Thread(new RunnableAdapter(callable));
        thread.start();

        String[] exist = new String[] {"Good", "morning", "Bob", "and", "Alice"};
        Set<String> set = new HashSet<>(Arrays.asList(exist));
        float i = 10/.75f;

        InputStream input = new FileInputStream("D:\\My_work\\左神算法资料\\Design_Patterns\\src\\main\\java\\filepath\\test.txt");
        Reader reader = new InputStreamReader(input, "UTF-8");
        readText(reader);
        System.out.println(i);
    }

    private static void readText(Reader reader) {
    }

}
