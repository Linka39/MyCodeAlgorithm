package structure.flyweight;

import java.util.HashMap;
import java.util.Map;

public class Student {
    // 持有缓存
    private static final Map<String, Student> cache = new HashMap<>();

    // 静态工厂方法
    public static Student create(int id, String name) {
        String key = id + "_" + name;
        // 先查缓存
        Student stu = cache.get(key);
        if (stu == null) {
            // 缓存中没有，进行创建
            System.out.println(String.format("create new Student(%s, %s)", id, name));
            Student student = new Student(id, name);
            cache.put(key, student);
            return student;
        } else {
            //缓存中存在
            System.out.println(String.format("already have!! return cache Student(%s, %s)", id, name));
        }
        return stu;
    }

    private final int id;
    private final String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
