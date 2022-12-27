package multithreading.code01;

/**
 * 多窗口购票，购票时展示相应窗口并进行一段时间睡眠
 */
public class BuyTickets {

    public static void main(String[] args) {
        Sell sell = new BuyTickets().new Sell();
        new Thread(sell,"窗口一").start();
        new Thread(sell,"窗口二").start();
        new Thread(sell,"窗口三").start();
    }
    class Sell implements Runnable{
        private Integer Num = 15;
        @Override
        public void run() {
            while (true){
                // synchronized (this){ 不能用this，因为this是指针，jvm会按偏向锁的形式加给窗口一的线程
                synchronized (Num){
                    if (Num>0){
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()+"正在售票"+Num--);
                    }else {
                        break;
                    }
                }
            }
        }
    }
}
