package multithreading.code01;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.LockSupport;

public class ByOrder {
    public static void main(String[] args) throws InterruptedException {
        printOrder1();
    }

    public static void printOrder1() throws InterruptedException {
        Order order = new ByOrder().new Order();
        int k = 4;
        while (k-->0){
            Thread t1 = new Thread(() -> order.print(),"t1");
            Thread t2 = new Thread(() -> order.print(),"t2");
            Thread t3 = new Thread(() -> order.print(),"t3");
            t1.start();
            t2.start();
            t3.start();

            t1.join();
            t2.join();
            t3.join();
        }
        System.out.println("---------------------");
    }

    public static void printOrder2() throws InterruptedException {
//        Semaphore s = new Semaphore(1);
        Order2 order = new ByOrder().new Order2();
        int k = 4;
        while (k-->0){
            CountDownLatch coutdown = new CountDownLatch(3);
            Thread t1 = new Thread(() -> order.print(),"t1");
            Thread t2 = new Thread(() -> order.print(),"t2");
            Thread t3 = new Thread(() -> order.print(),"t3");
            t1.start();
            LockSupport.unpark(t1);
            coutdown.countDown();
            t2.start();
            LockSupport.unpark(t2);
            coutdown.countDown();
            t3.start();
            LockSupport.unpark(t3);
            coutdown.countDown();

            coutdown.await();
        }
    }

    class Order {
        public void print(){
            System.out.println("打印"+Thread.currentThread().getName());
        }
    }
    class Order2 {
        public void print(){
            LockSupport.park();
            System.out.println("打印"+Thread.currentThread().getName());
        }
    }
}
