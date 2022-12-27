package multithreading.code01;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

public class ByOrder {
    public static void main(String[] args) throws InterruptedException {
        printOrder2();
    }

    public static void printOrder1() throws InterruptedException {
        Order order = new ByOrder().new Order();
        Thread t1 = new Thread(() -> order.print(),"t1");
        Thread t2 = new Thread(() -> order.print(),"t2");
        Thread t3 = new Thread(() -> order.print(),"t3");
        t1.start();
        t1.join();
        t2.start();
        t2.join();
        t3.start();
        t3.join();
        System.out.println("---------------------");
    }

    public static void printOrder2() throws InterruptedException {
        Semaphore s = new Semaphore(1);
        Order2 order = new ByOrder().new Order2();
        Thread t1 = new Thread(() -> order.print(),"t1");
        Thread t2 = new Thread(() -> order.print(),"t2");
        Thread t3 = new Thread(() -> order.print(),"t3");
        t1.start();
        LockSupport.unpark(t1);
        t2.start();
        LockSupport.unpark(t2);
        t3.start();
        LockSupport.unpark(t3);
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
