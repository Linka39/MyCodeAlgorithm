package behaviour.chainOfResponsibility.myhandler;

import behaviour.chainOfResponsibility.Handler;
import behaviour.chainOfResponsibility.Request;

import java.util.ArrayList;
import java.util.List;

public class HandlerChain {
    // 持有所有Handler:
    private List<Handler> handlers = new ArrayList<>();

    public void addHandler(Handler handler) {
        this.handlers.add(handler);
    }
    public boolean process(Request request) {
        Boolean r = false;
        // 依次调用每个Handler:
        for (Handler handler : handlers) {
            r = handler.process(request);
            if (r != null) {
                // 如果返回TRUE或FALSE，处理结束:
                System.out.println(request + "  " + (r ? "Approved by " : "Denied by ") + handler.getClass().getSimpleName());
                if (r){
                    return r;
                }
            }
        }

        try {
            throw new RuntimeException("Could not handle request: " + request);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
//        throw new RuntimeException("Could not handle request: " + request);
        return r;
    }
}
