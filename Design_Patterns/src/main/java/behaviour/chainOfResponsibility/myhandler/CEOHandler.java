package behaviour.chainOfResponsibility.myhandler;

import behaviour.chainOfResponsibility.Handler;
import behaviour.chainOfResponsibility.Request;

import java.math.BigDecimal;

public class CEOHandler implements Handler {
    @Override
    public Boolean process(Request request) {

        // 对Bob有偏见:
       return !request.getName().equalsIgnoreCase("bob");
    }
}
