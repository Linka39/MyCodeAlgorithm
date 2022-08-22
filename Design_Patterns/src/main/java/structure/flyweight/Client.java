package structure.flyweight;

/**
 * 在实际应用中，享元模式主要应用于缓存，即客户端如果重复请求某些对象，
 * 不必每次查询数据库或者读取文件，而是直接返回内存中缓存的数据。
 */
public class Client {
    public static void main(String[] args) {
//        testInterge();
        testStudent();
    }

    /**
     * 享元模式在Java标准库中有很多应用。我们知道，包装类型如Byte、Integer都是不变类，因此，反复创建同一个值相同的包装类型是没有必要的。
     * 以Integer为例，如果我们通过Integer.valueOf()这个静态工厂方法创建Integer实例，当传入的int范围在-128~+127之间时，会直接返回缓存的Integer实例：
     */
    public static void testInterge() {
        Integer n1 = Integer.valueOf(100);
        Integer n2 = 100;
        System.out.println(n1 == n2); // true
    }

    public static void testStudent() {
        Student stu1 = Student.create(1, "张三");
        Student stu2 = Student.create(2, "王五");
        Student stu3 = Student.create(1, "张三");
    }

}
