package mydesign;
import java.lang.annotation.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 思路：1、变量包定义 Message 作为消息实体，setCode, getCode来获取上个处理器是否正确处理数据。使用setData, getData方法来获取数据。
 * 2、接口定义 IProcesser(process方法进行业务处理，getServiceType获取服务类型为入栈或出栈，getServiceName获取服务名字)接口，
 * 3、常量包定义ProcessConstant常量(包含SERVICE_TYPE入栈出栈服务类型, method_type方法类型)
 * 4、注解定义 AProcesser(Retention, Target(ElementType.Type)), 包含id,name,method,service 等
 * 5、定义BaseProcesser来实现接口，首先构造方法通过反射将注解元数据注入到属性中.
 *      process 方法首先判断 message 中的 method类型判断执行相应的commonHandler(先执行check,根据结果再执行execute)方法，feedback 或 t1check 方法。
 * 6、定义ProcessPipline类，内部包含List<IProcesser> inProcesserArr，outProcesserArr。使用addProcesser 方法来添加到处理器数组中，并根据getServiceType获取服务类型放到相应的list中。
 *      使用handle()方法来依次处理链条上的处理器，首先遍历 inProcesserArr 执行process方法，获取到 message 对象，如果当前message的code>0则继续执行，小于0时则停止执行并打印。
 *      接着出栈处理，遍历 outProcesserArr 执行process方法，获取到 message 对象，code大于0时继续执行。
 * 7、定义Springutils类，使用initData()方法来将注解的类放到IOC容器中(k为id, v为实例)，使用getBean()方法来获取实例.
 * 8、使用注解来定义BankAccountProcesser, RegidAccountProcesser 作为入栈处理器处理消息（判断当前资金是否可支持服务的进行）, MessageProcesser 作为出栈处理器，在入栈结束时执行
 *
 * 客户端定义 IProcesser，ProcessPipline，先将处理器添加的ProcessPipline中，然后ProcessPipline通过hanlder方法让链条上的方法依次进行。
 */
public class ProcessChain {
    // 过滤不通过返回 code-1
    public static void main(String[] args) {
        Springutil3.initData();
        BankAccountProcesser bankAccountProcesser = (BankAccountProcesser)Springutil3.getBean("BankAccountProcesser");
        RegidAccountProcesser regidAccountProcesser = (RegidAccountProcesser)Springutil3.getBean("RegidAccountProcesser");
        MessageProcesser messageProcesser = (MessageProcesser)Springutil3.getBean("MessageProcesser");

        ProcessPipline processPipline = new ProcessPipline();
        processPipline.addProcesser(messageProcesser);
        processPipline.addProcesser(regidAccountProcesser);
        processPipline.addProcesser(bankAccountProcesser);
        Message3 message = new Message3();
        message.setData("balance", 25+"");
//        message.setMethod(ProcessConstant.METHOD_T1CHECK);
        message.setMethod(ProcessConstant.METHOD_SERVICE);
        processPipline.handle(message);
    }
}

class Springutil3{
    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    public static void initData(){
        serviceMap.put(BankAccountProcesser.class.getAnnotation(AProcesser.class).id(),new BankAccountProcesser());
        serviceMap.put(RegidAccountProcesser.class.getAnnotation(AProcesser.class).id(),new RegidAccountProcesser());
        serviceMap.put(MessageProcesser.class.getAnnotation(AProcesser.class).id(),new MessageProcesser());
    }
    public static Object getBean(String k){
        return serviceMap.get(k);
    }
}
@AProcesser(id = "MessageProcesser", name = "短信处理器", type = ProcessConstant.PROCESS_OUTBOUND)
class MessageProcesser extends BaseProcesser implements IProcesser{

    public Message3 execute(Message3 message){
        System.out.println("短信发送成功！");
        return message;
    }
}
@AProcesser(id = "RegidAccountProcesser", name = "股东账户处理器")
class RegidAccountProcesser extends BaseProcesser implements IProcesser{
    public Message3 check(Message3 message){
        int balance = Integer.parseInt(message.getData("balance"));
        if (balance > 10){
            System.out.println("股东账户处理预扣款成功！");
            message.setData("balance", (balance-=10)+"");
        }else {
            System.out.println("股东账户处理预扣款失败！");
            message.setCode(-1);
        }
        return message;
    }
    public Message3 execute(Message3 message){
        System.out.println("股东账户处理成功");
        return message;
    }
}
@AProcesser(id = "BankAccountProcesser", name = "存管银行处理器")
class BankAccountProcesser extends BaseProcesser implements IProcesser{
    public Message3 check(Message3 message){
        int balance = Integer.parseInt(message.getData("balance"));
        if (balance > 10){
            System.out.println("存管账户处理预扣款成功！");
            message.setData("balance", (balance-=10)+"");
        }else {
            System.out.println("存管账户处理预扣款失败！");
            message.setCode(-1);
        }
        return message;
    }
    public Message3 execute(Message3 message){
        System.out.println("存管账户处理成功");
        return message.setCode(1);
    }
    public Message3 feedback(Message3 message){
        System.out.println("存管账户(feedback)成功");
        return message.setCode(1);
    }
    public Message3 t1check(Message3 message){
        System.out.println("存管账户(t1check)成功");
        return message.setCode(1);
    }
}
class BaseProcesser implements IProcesser{
    private String id;
    private String name;
    private int type;
    public BaseProcesser(){
        AProcesser aProcesser = this.getClass().getAnnotation(AProcesser.class);
        this.id=aProcesser.id();
        this.name=aProcesser.name();
        this.type=aProcesser.type();
    }

    public Message3 process(Message3 inPackt){
        String method = inPackt.getMethod();
        switch (method){
            case ProcessConstant.METHOD_SERVICE:
                return commonHandle(inPackt);
            case ProcessConstant.METHOD_FEEDBACK:
                return feedback(inPackt);
            case ProcessConstant.METHOD_T1CHECK:
                return t1check(inPackt);
        }
        return commonHandle(inPackt);
    }
    public int getProcessType(){
        return this.type;
    }
    public String getProcessName(){
        return this.name;
    }
    public Message3 commonHandle(Message3 message){
        Message3 checkOut = this.check(message);
        if (checkOut.getCode()<0 || checkOut.getCode()==null)
            return checkOut;
        return execute(message);
    }
    public Message3 check(Message3 message){
        return message.setCode(1);
    }
    public Message3 execute(Message3 message){
        return message;
    }
    public Message3 feedback(Message3 message){
        return message;
    }
    public Message3 t1check(Message3 message){
        return message;
    }
}
class ProcessPipline{
    private List<IProcesser> inProcessArr = new ArrayList<>();
    private List<IProcesser> outProcessArr = new ArrayList<>();
    public void addProcesser(IProcesser Processer){
        if (Processer.getProcessType()==ProcessConstant.PROCESS_INBOUND)
            inProcessArr.add(Processer);
        else if (Processer.getProcessType()==ProcessConstant.PROCESS_OUTBOUND)
            outProcessArr.add(Processer);
    }
    public Message3 handle(Message3 message){
        for (IProcesser Process:inProcessArr){
            message = Process.process(message);
            System.out.println("请求已被【"+ Process.getProcessName()+ "】处理："+
                    (message.getCode()>0 ? "处理成功。":"处理失败，开始出栈处理."));
            if (message.getCode()<0) break;
        }
        for(IProcesser Process:outProcessArr){
            message = Process.process(message);
            System.out.println("请求已被【"+ Process.getProcessName()+ "】出栈处理："+
                    (message.getCode()>0 ? "处理成功。":"处理失败，处理结束."));
            if (message.getCode()<0) break;
        }
        return message;
    }
}
interface IProcesser{
    public Message3 process(Message3 inPackt);
    public int getProcessType();
    public String getProcessName();
}

class Message3{
    private Map<String,String> data = new HashMap<>();
    public Message3(){
        data.put("code","1");
        data.put("note","");
    }
    public Message3 setMethod(String m){
        data.put("method", m);
        return this;
    }
    public String getMethod(){
        return data.get("method");
    }
    public Message3 setData(String k, String v){
        data.put(k,v);
        return this;
    }
    public String getData(String k){
        return data.get(k);
    }
    public Message3 setCode(int code){
        data.put("code", code+"");
        return this;
    }
    public Integer getCode(){
        return Integer.parseInt(data.get("code"));
    }
}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface AProcesser{
    String id();
    String name();
    int type() default ProcessConstant.PROCESS_INBOUND;
}
class ProcessConstant{
    public static final String METHOD_FEEDBACK = "feedback";
    public static final String METHOD_SERVICE = "service";
    public static final String METHOD_T1CHECK = "t1check";

    public static final int PROCESS_INBOUND = 1;
    public static final int PROCESS_OUTBOUND= 2;
}
