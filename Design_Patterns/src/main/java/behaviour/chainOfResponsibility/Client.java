package behaviour.chainOfResponsibility;

import behaviour.chainOfResponsibility.myhandler.CEOHandler;
import behaviour.chainOfResponsibility.myhandler.DirectorHandler;
import behaviour.chainOfResponsibility.myhandler.HandlerChain;
import behaviour.chainOfResponsibility.myhandler.ManagerHandler;

import java.math.BigDecimal;

public class Client {
    public static void main(String[] args) {
        testChain();
    }
    public static void testChain() {
        HandlerChain chain = new HandlerChain();
        chain.addHandler(new ManagerHandler());
        chain.addHandler(new DirectorHandler());
        chain.addHandler(new CEOHandler());

        chain.process(new Request("BoB", new BigDecimal("123.45")));
        chain.process(new Request("Alice", new BigDecimal("123400.56")));
        chain.process(new Request("Bill", new BigDecimal("12345.67")));
        chain.process(new Request("John", new BigDecimal("123456.78")));
    }
}
