package mydesign;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
/**
 * 思路：1、变量包定义 Message 作为消息实体。使用setTopic, getTopic方法来获取主题
 * 2、接口定义 IConsumer(onPublished 主题有新消息时，onNewTopic 当有新主题出现时)接口，
 * 3、常量包定义ConsumerConstant常量(包含topic, method_type类型)
 * 4、注解定义 AConsumer(Retention, Target(ElementType.Type)), 包含id,name,method,topic 等
 * 5、定义BaseConsumer来实现接口，首先构造方法通过反射将注解元数据注入到属性中.
 *      onPublished方法首先判断 message 是否为当前主题，是的话根据 message 的 method类型来判断执行相应的commonHandler方法，feedback 或 t1check 方法。
 * 6、定义Broker类，内部包含List<IConsumer> consumerArr，使用addConsumer，removeConsumer方法来添加到消费者数组中。
 *      内部包含Map<Str,List<Message>> mesMap，使用 addMessage 方法时判断当前message的topic，mesMap 中是否包含，不包含的话先创建相关主题和list, 然后遍历执行 onNewTopic 方法，打印。。。
 *      如果mesMap中包含相关主题时，mesMap对应主题里的list先加入相关 message，然后consumerArr中遍历执行 onPublished 方法。打印。。。
 * 7、定义Springutils类，使用initData()方法来将注解的类放到IOC容器中(k为id, v为实例)，使用getBean()方法来获取实例.
 * 8、使用注解来定义BankAccountConsumer, BeijingBankAccountServiceSave 订阅同一topic（bank）, RegidAccountConsumer 订阅股东号的topic
 *
 * 客户端定义 IConsumer，Broker，先将消费者添加的broker中，然后braker通过 addMessage 方法来添加消息来触发 IConsumer 的订阅实现。
 */
public class observe {
    public static void main(String[] args) {
        Springutils2.initData();
        Broker broker = new Broker();
        BankAccountConsumer bankAccountConsumer = (BankAccountConsumer)Springutils2.getBean("BankAccountConsumer");
        BeijingBankAccountConsumer beijingBankAccountConsumer = (BeijingBankAccountConsumer)Springutils2.getBean("BeijingBankAccountConsumer");
        broker.addCosumer(bankAccountConsumer);
        broker.addCosumer(beijingBankAccountConsumer);
        RegidAccountConsumer regidAccountConsumer = (RegidAccountConsumer)Springutils2.getBean("RegidAccountConsumer");
        broker.addCosumer(regidAccountConsumer);

        Message message = new Message().setTopic(ConsumerConstant.TOPIC_BANK).setMethod(ConsumerConstant.METHOD_FEEDBACK);
        broker.addMessage(message);
        System.out.println("--------------------------");
        message = new Message().setTopic(ConsumerConstant.TOPIC_REGID);
        broker.addMessage(message);
        System.out.println("--------------------------");
        message = new Message().setTopic(ConsumerConstant.TOPIC_BANK);
        broker.addMessage(message);
        System.out.println("--------------------------");
    }
}

class Springutils2{
    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();
    public static void initData(){
        serviceMap.put(BankAccountConsumer.class.getAnnotation(AConsumer.class).id(),new BankAccountConsumer());
        serviceMap.put(BeijingBankAccountConsumer.class.getAnnotation(AConsumer.class).id(),new BeijingBankAccountConsumer());
        serviceMap.put(RegidAccountConsumer.class.getAnnotation(AConsumer.class).id(),new RegidAccountConsumer());
    }
    public static Object getBean(String k){
        return serviceMap.get(k);
    }
}
@AConsumer(id = "BeijingBankAccountConsumer", topic = ConsumerConstant.TOPIC_BANK, name = "北京银行变更服务", weight = 2)
class BeijingBankAccountConsumer extends BaseConsumer implements IConsumer{
    public OutPackt check(Message message) {
        OutPackt outPackt = new OutPackt();
        if(message.getData("bankName")==null || !message.getData("bankName").equals("北京银行")){
            System.out.println("北京银行变更处理检查未通过");
            return outPackt;
        }
        System.out.println("北京银行变更处理检查通过");
        return outPackt.setCode(1);
    }
    public OutPackt execute(Message message) {
        System.out.println("北京银行变更数据处理成功");
        return new OutPackt().setCode(1);
    }
}
@AConsumer(id = "BankAccountConsumer", topic = ConsumerConstant.TOPIC_BANK, name = "存管银行变更服务", weight = 2)
class BankAccountConsumer extends BaseConsumer implements IConsumer{
    public OutPackt check(Message message) {
        System.out.println("存管银行变更处理检查通过");
        return new OutPackt().setCode(1);
    }

    public OutPackt execute(Message message) {
        System.out.println("存管银行变更数据处理成功");
        return new OutPackt().setCode(1);
    }
    public OutPackt feedback(Message message) {
        System.out.println("feedback: 存管银行feedback成功");
        return new OutPackt().setCode(1);
    }
    public OutPackt t1check(Message message) {
        System.out.println("t1check: 存管银行t1check成功");
        return new OutPackt().setCode(1);
    }
}
@AConsumer(id = "RegidAccountConsumer", topic = ConsumerConstant.TOPIC_REGID, name = "股东号变更服务", weight = 2)
class RegidAccountConsumer extends BaseConsumer implements IConsumer{
    public OutPackt check(Message message) {
        System.out.println("股东号处理检查通过");
        return new OutPackt().setCode(1);
    }

    public OutPackt execute(Message message) {
        System.out.println("股东号数据处理成功");
        return new OutPackt().setCode(1);
    }
}
class Broker{
    private Map<String, List<Message>> topicMap = new ConcurrentHashMap<>();
    private List<IConsumer> consumerArr = new ArrayList<>();
    // onPublish,循环list
    public void addCosumer(IConsumer consumer){
        this.consumerArr.add(consumer);
    }
    public void removeCosumer(IConsumer consumer){
        this.consumerArr.remove(consumer);
    }
    public void addMessage(Message message){
        String topic = message.getTopic();
        if (!this.topicMap.containsKey(topic)){
            this.topicMap.put(message.getTopic(), new ArrayList<Message>());
            this.consumerArr.forEach(o -> o.onAddTopic(message));
        }
        this.topicMap.get(topic).add(message);
        this.consumerArr.forEach(o -> o.onPublished(message));
    }
}
class BaseConsumer implements IConsumer{
    private String topic;
    private String id;
    private String name;

    public BaseConsumer(){
        this.topic = this.getClass().getAnnotation(AConsumer.class).topic();
        this.id = this.getClass().getAnnotation(AConsumer.class).id();
        this.name = this.getClass().getAnnotation(AConsumer.class).name();
    }

    public void onPublished(Message message){
        if (message.getTopic().equals(topic)){
            this.handle(message);
            System.out.println("["+topic+"]"+"主题已被【"+name+"】消费");
        }
    }
    public void onAddTopic(Message message){
        System.out.println("新主题["+message.getTopic()+"]已发布, 【"+name+"】已收到通知。。");
    }
    public void handle(Message message){
        OutPackt outPackt = new OutPackt();
        switch (message.getMethod()){
            case ConsumerConstant.METHOD_SERVICE:
                outPackt = commonHandle(message);
                break;
            case ConsumerConstant.METHOD_FEEDBACK:
                outPackt = this.feedback(message);
                break;
            case ConsumerConstant.METHOD_T1CHECK:
                outPackt = this.t1check(message);
                break;
        }
        // 对消息处理的结果进行记录
        outPackt.getCode();
    }
    public OutPackt commonHandle(Message message){
        OutPackt checkOut = this.check(message);
        if (checkOut.getCode()<0 || checkOut.getCode()==null)
            return checkOut;
        return execute(message);
    }
    protected OutPackt check(Message message){
        return new OutPackt();
    }
    protected OutPackt execute(Message message){
        return new OutPackt();
    }
    protected OutPackt feedback(Message message){
        return new OutPackt();
    }
    protected OutPackt t1check(Message message){
        return new OutPackt();
    }
}
interface IConsumer{
    public void onPublished(Message message);
    public void onAddTopic(Message message);
}
class Message{
    private Map<String,String> data = new HashMap<>();
    public Message(){
        data.put("topic","");
        data.put("data","");
        data.put("method",ConsumerConstant.METHOD_SERVICE);
    }
    public Message setData(String k,String v){
        data.put(k, v);
        return this;
    }
    public String getData(String k){
        return data.get(k);
    }
    public Message setTopic(String topic){
        data.put("topic", topic);
        return this;
    }
    public Message setMethod(String v){
        data.put("method", v);
        return this;
    }
    public String getTopic(){
        return data.get("topic");
    }
    public String getMethod(){
        return data.get("method");
    }
}

class ConsumerConstant{
    public static final String METHOD_FEEDBACK = "feedback";
    public static final String METHOD_SERVICE = "service";
    public static final String METHOD_T1CHECK = "t1check";

    public static final String TOPIC_BANK = "bank";
    public static final String TOPIC_REGID= "regid";
}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface AConsumer{
    String id();
    String topic();
    String name();
    int weight() default 1;
}
