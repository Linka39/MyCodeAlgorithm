package establish.factory;

import java.math.BigDecimal;

/**
 * 静态工厂方法
 */
public class NumberFactoryStatic {
    public static Number parse(String s) {
        return new BigDecimal(s);
    }
}
