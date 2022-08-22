package establish.factory;

import java.math.BigDecimal;

public class NumberBigFactoryImpl implements NumberFactory{
    @Override
    public Number parse(String s) {
        return new BigDecimal(s + "00");
    }
}
