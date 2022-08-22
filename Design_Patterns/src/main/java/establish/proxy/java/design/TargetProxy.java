package establish.proxy.java.design;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 要提前引入依赖
 *         <dependency>
 *             <groupId>cglib</groupId>
 *             <artifactId>cglib</artifactId>
 *             <version>3.3.0</version>
 *         </dependency>
 */
public class TargetProxy implements InvocationHandler, SingerInter {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("交钱...");
//        if (method.getName().equals("sing")){
//            sing();
//        }
        method.invoke(this, args);
        System.out.println("结束...");
        return method;
    }

    @Override
    public void sing() {
        System.out.println("唱歌了");
    }

    @Override
    public void dance() {
        System.out.println("跳舞了");
    }

    public static void main(String[] args) {
        SingerInter singer =  (SingerInter)Proxy.newProxyInstance(SingerInter.class.getClassLoader(),
                new Class[]{SingerInter.class},
                new TargetProxy());
        singer.sing();
        singer.dance();
    }
}
