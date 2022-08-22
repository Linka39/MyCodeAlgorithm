package establish.proxy.java.design;

public class ProxyDemo {
    public static void main(String[] args) {
        Singer singer = new SingerSub();
        singer.singing();
        singer.dance();
    }
}

class Singer {
    public void dance(){
        System.out.println("can dance");
    }
    public void singing(){
        System.out.println("can singing");
    }
}

class SingerSub extends Singer {
    public void dance(){
        System.out.println("经纪人签合同");
        super.dance();
        System.out.println("收钱");
    }
    public void singing(){
        System.out.println("经纪人签合同");
        super.singing();
        System.out.println("收钱");
    }
}


