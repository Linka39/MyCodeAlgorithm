package mydesign;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

/**
 * 思路：1、变量包定义 InPacket入参包，OutPacket出参包。
 * 2、接口定义IService(check，execute,feedback, t1check)，RpcService(_exec)接口，
 * 3、常量包定义AmsConstant常量(包含service_type,method_type类型)
 * 4、注解定义 AmsService(Retention, Target(ElementType.Type)), 包含id,name,method,service等
 * 5、定义BaseService来实现两个接口，首先构造方法通过反射将注解元数据注入到属性中.
 *      内部统一使用_exec方法执行，根据method类型执行不同的方法，如果是method为service，根据不同的service类型来执行不同的 handler。commonHandler先执行check，再执行execute。
 * 6、定义Springutils类，使用initData()方法来将注解的类放到IOC容器中(k为id, v为实例)，使用getBean()方法来获取实例.
 * 7、使用注解来定义BankAccountServiceInit, BankAccountServiceSave, BankAccountService方法，统一执行_exec方法，通过不同的serviceType来执行
 */
public class main {
    public static void main(String[] args) {
        Springutils.initService();

        InPackt inPackt = new InPackt();
        BankAccountServiceInit bankAccountServiceInit = (BankAccountServiceInit)Springutils.getBean("BankAccountServiceInit");
        bankAccountServiceInit._exec(inPackt);//继承后统一使用 _exec()执行逻辑
        System.out.println("-------------------------");
        BankAccountServiceSave bankAccountServiceSave = (BankAccountServiceSave)Springutils.getBean("BankAccountServiceSave");
        bankAccountServiceSave._exec(inPackt);
        System.out.println("-------------------------");

        inPackt.setData("bankName","银行");
        inPackt.setData("bankName","北京银行");
        inPackt.setData("opType","2");
        BankAccountService bankAccountService = (BankAccountService)Springutils.getBean("BankAccountService");
        bankAccountService._exec(inPackt);
        System.out.println("-------------------------");

        inPackt=new InPackt();
        inPackt.setData("method_type",AmsConstant.METHOD_FEEDBACK);
        bankAccountService._exec(inPackt);
        System.out.println("-------------------------");
        inPackt.setData("method_type",AmsConstant.METHOD_T1CHECK);
        bankAccountService._exec(inPackt);
        System.out.println("-------------------------");
    }
}
class Springutils{
    private static Map<String,Object> serviceMap = new HashMap<>();
    public static void initService(){
        serviceMap.put(BankAccountServiceInit.class.getAnnotation(RpcService.class).id(),new BankAccountServiceInit());
        serviceMap.put(BankAccountService.class.getAnnotation(RpcService.class).id(),new BankAccountService());
        serviceMap.put(BankAccountServiceSave.class.getAnnotation(RpcService.class).id(),new BankAccountServiceSave());
    }
    public static Object getBean(String name){
       return serviceMap.get(name);
    }
}
@RpcService(name="存管银行变更init", id="BankAccountServiceInit", type = AmsConstant.SERVICE_BUSINESS_INIT)
class BankAccountServiceInit extends BaseService implements IService{   // 不直接继承接口，避免接口改动其他接口都改动的影响
    public OutPackt check(InPackt inPackt) {
        System.out.println("存管银行变更init检查通过");
        return null;
    }
    public OutPackt execute(InPackt inPackt) {
        System.out.println("存管银行变更init数据获取成功");
        return null;
    }
}
@RpcService(name="存管银行变更save", id="BankAccountServiceSave", type = AmsConstant.SERVICE_BUSINESS_SAVE)
class BankAccountServiceSave extends BaseService implements IService{   // 不直接继承接口，避免接口改动其他接口都改动的影响
    public OutPackt check(InPackt inPackt) {
        System.out.println("存管银行变更save检查通过");
        return null;
    }
    public OutPackt execute(InPackt inPackt) {
        System.out.println("存管银行变更save，数据以保存");
        return null;
    }
}
class BankAccountDao{
    public OutPackt bankAccountAction(InPackt inPackt){
        String opType = inPackt.getData("opType");
        switch (opType){
            case "1":
                System.out.println("存管开户成功");
                break;
            case "2":
                System.out.println("存管修改成功");
                break;
            case "3":
                System.out.println("存管删除成功");
                break;
        }
        return new OutPackt().setCode(1);
    }
}
@RpcService(name="存管银行变更", id="BankAccountService", type = AmsConstant.SERVICE_BUSINESS_INIT)
class BankAccountService extends BaseService implements IService{   // 不直接继承接口，避免接口改动其他接口都改动的影响
    public OutPackt check(InPackt inPackt) {
        OutPackt outPackt = new OutPackt();
        if(inPackt.getData("bankName")==null || !inPackt.getData("bankName").equals("北京银行")){
            System.out.println("存管银行变更处理检查未通过");
            return outPackt;
        }
        System.out.println("存管银行变更处理检查通过");
        return outPackt.setCode(1);
    }

    public OutPackt execute(InPackt inPackt) {
        OutPackt outPackt = new BankAccountDao().bankAccountAction(inPackt);
        System.out.println("存管银行变更数据处理成功");
        return outPackt;
    }

    public OutPackt feedback(InPackt inPackt) {
        System.out.println("feedback: 存管银行feedback成功");
        return new OutPackt().setCode(1);
    }
    public OutPackt t1check(InPackt inPackt) {
        System.out.println("t1check: 存管银行t1check成功");
        return new OutPackt().setCode(1);
    }
}

abstract class BaseService implements RpcExecService,IService{
    private String SERVICE_NAME;    // 服务类型，方法类型每个服务是不一样的，可以通过注解中获取并赋值
    private String METHOD_TYPE;     // 无状态的，因为每个服务的服务名，类型都是固定的，请求包是有状态的（可以放在threadlocal中保存）
    private int SERVICE_TYPE;
    private boolean async;
    public BaseService(){
        RpcService rpcService = this.getClass().getAnnotation(RpcService.class);
        this.SERVICE_TYPE = rpcService.type();
        this.SERVICE_NAME = rpcService.name();
    }

    public OutPackt _exec(InPackt inPackt){

        OutPackt outPackt = new OutPackt();
        // rpc中会由客户端从注册中心获取ip地址，负载均衡选一个ip与服务端连接得到 channel，通过序列化压缩传入请求包
        // 服务端通过反序列化解压后得到请求包，根据请求包的服务名alias，得到本地map中的服务实例，调用实例的 _exec 方法得到响应包
        String method_type = inPackt.getData("method_type");
        if (method_type.equals(AmsConstant.METHOD_FEEDBACK)){
            outPackt = this.feedback(inPackt);
        }else if (method_type.equals(AmsConstant.METHOD_T1CHECK)){
            outPackt = this.t1check(inPackt);
        }else if (method_type.equals(AmsConstant.METHOD_SERVICE)){
            outPackt = this.handle(inPackt);
        }
//        respHeader.put(BaseServiceConstant.RETURN_TYPE_IN_HEADER, outPacket.getClass().getName());
//        responseObserver.onNext(BaseServiceProto.BaseResponse.newBuilder().setBody(outPacket.toString()).build());
//        responseObserver.onCompleted();
        return outPackt;
    }
    public OutPackt handle(InPackt inPackt){
        switch (this.SERVICE_TYPE){
            case AmsConstant.SERVICE_BUSINESS_INIT:
                return this.BusinessInitHandle(inPackt);
            case AmsConstant.SERVICE_BUSINESS_QUERY:
                return this.BusinessQueryHandle(inPackt);
            case AmsConstant.SERVICE_BUSINESS_SAVE:
                return this.BusinessSaveHandle(inPackt);
            case AmsConstant.SERVICE_BUSINESS_PROCESS:
                return this.BusinessProcessHandle(inPackt);
            default:
                return this.CommonHandle(inPackt);
        }
    }

    public OutPackt CommonHandle(InPackt inPackt){
        IService svc = (IService)this;
        OutPackt checkOut = svc.check(inPackt);
        if (checkOut == null || checkOut.getCode()>0)
            return svc.execute(inPackt);
        else return checkOut;
    }
    public OutPackt BusinessProcessHandle(InPackt inPackt){
        System.out.println("公共业务处理，生成业务处理记录，保存服务状态");
        // 分布式锁，生成业务请求时使用redis锁，key为请求串的 md5key，避免分布式同一请求多次生成
        // threadLocal，生成的请求处理每个线程保存一份副本数据，因为服务是单例的，需要对线程进行保存。
        return this.CommonHandle(inPackt);
    }
    public OutPackt BusinessQueryHandle(InPackt inPackt){
        System.out.println("公共-查询处理");
        return this.CommonHandle(inPackt);
    }
    public OutPackt BusinessInitHandle(InPackt inPackt){
        // 获取公共数据，例如客户信息，业务配置信息(办理模式，业务层级，不同业务对应不同层级客户号)
        System.out.println("公共-初始化处理。。。");
        return this.CommonHandle(inPackt);
    }
    public OutPackt BusinessSaveHandle(InPackt inPackt){
        // 保存处理记录
        System.out.println("公共-保存处理。。。");
        return this.CommonHandle(inPackt);
    }
    public OutPackt check(InPackt inPackt) {
        return null;
    }

    public OutPackt execute(InPackt inPackt) {
        return null;
    }

    public OutPackt feedback(InPackt inPackt) {
        return null;
    }

    public OutPackt t1check(InPackt inPackt) {
        return null;
    }
}

class AmsConstant{
    public static String METHOD_FEEDBACK = "feedback";
    public static String METHOD_SERVICE = "service";
    public static String METHOD_T1CHECK = "t1check";

    public static final int SERVICE_BUSINESS_QUERY=0;
    public static final int SERVICE_BUSINESS_INIT = 1;
    public static final int SERVICE_BUSINESS_SAVE = 2;
    public static final int SERVICE_BUSINESS_PROCESS =3;
}

class OutPackt{
    private Map<Object, Object> data;

    public OutPackt(){
        this.data = new HashMap<>();
        this.data.put("code", -1);
        this.data.put("note", "");
        this.data.put("records", null);
    }
    // 建造者模式，构建对象并返回
    public OutPackt setCode(Integer code){
        this.data.put("code", code);
        return this;
    }
    public Integer getCode(){
        return (Integer)this.data.get("code");
    }
}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface RpcService{
    String id() default "";
    String name() default "";
    int type() default AmsConstant.SERVICE_BUSINESS_PROCESS;
}

class InPackt{
    private Map<Object, Object> data;
    public InPackt(){
        this.data = new HashMap<>();
        this.data.put("method_type", "service");
    }
    public InPackt setData(String key, Object v){
        this.data.put(key, v);
        return this;
    }
    public String getData(String key){
        return (String)this.data.get(key);
    }
}
interface IService{
    public OutPackt check(InPackt inPackt);
    public OutPackt execute(InPackt inPackt);
    public OutPackt feedback(InPackt inPackt);
    public OutPackt t1check(InPackt inPackt);
}
interface RpcExecService{
    public OutPackt _exec(InPackt inPackt);
}
