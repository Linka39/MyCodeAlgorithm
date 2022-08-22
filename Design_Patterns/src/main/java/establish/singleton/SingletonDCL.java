package establish.singleton;

public class SingletonDCL {
    // 主要是预防指令重排
    private static volatile SingletonDCL instance = null;
    private SingletonDCL(){}

    public static SingletonDCL getInstance(){
        if (instance == null){
            synchronized (SingletonDCL.class) {
                if (instance == null) {
                    instance = new SingletonDCL();
                }
            }
        }
        return instance;
    }

}
