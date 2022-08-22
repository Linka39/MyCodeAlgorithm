package establish.factory;

public interface NumberFactory {
    // 创建方法:
    Number parse(String s);

    // 获取工厂实例:
    static NumberFactory getFactory() {
        return impl;
    }

    // 根据name 来获取获取工厂实例:
    static NumberFactory getFactory(String name) {
        return impl;
    }

    static NumberFactory impl = new NumberFactoryImpl();
}
