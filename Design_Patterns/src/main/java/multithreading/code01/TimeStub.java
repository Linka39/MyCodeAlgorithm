package multithreading.code01;
/**
 * 题目：倒计时
 * 实现一个计时器，每减1秒在控制台中打印
 */
public class TimeStub {
    public static void main(String[] args) {
        MyTimeStub myTimeStub = new MyTimeStub(5);
        new Thread(myTimeStub).start();
    }

}
class MyTimeStub implements Runnable{
    int time = 0;
    public MyTimeStub(int time){
        this.time = time;
    }

    @Override
    public void run() {
        while (true){
            if (time>0){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("倒计时"+time--);
            }else {
                break;
            }
        }
    }
}
