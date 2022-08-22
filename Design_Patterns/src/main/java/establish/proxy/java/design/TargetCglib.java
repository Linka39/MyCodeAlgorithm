package establish.proxy.java.design;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class TargetCglib implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("签合同");
        Object invoke = methodProxy.invokeSuper(o, objects);
        System.out.println("收钱");
        return invoke;
    }

    public static void main(String[] args) {
        Enhancer en = new Enhancer();
        en.setSuperclass(ZStar.class);
        en.setCallback(new TargetCglib(new ZStar()));
        ZStar star = (ZStar)en.create();
        star.dance();
        System.out.println("======================");
        star.sing();
    }

    private Object object;
    public TargetCglib(Object o){
        object = o;
    }
}


