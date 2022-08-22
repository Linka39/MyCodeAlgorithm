package establish.factory;

public class Client {

    public static void main(String[] args) {
        NumberFactory factory = NumberFactory.getFactory();
        Number result = factory.parse("123.456");
    }
}
