package multithreading.code01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerAndConsumer {
    /**
     * 题目：现在四个线程，可以操作初始值为0的一个变量
     * 实现两个线程对该变量 + 1，两个线程对该变量 -1
     * 实现交替10次
     *
     * 诀窍：
     * 1. 高内聚低耦合的前提下，线程操作资源类
     * 2. 判断 、干活、通知
     * 3. 多线程交互中，必须要防止多线程的虚假唤醒，也即（判断不能用if，只能用while）
     */
    public static void main(String[] args) {
        testPAC();
    }

    public static void testPAC() {
        MyResouce data = new MyResouce();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment(1);
            }
        }, "线程A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement(0);
            }
        }, "线程B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.increment(1);
            }
        }, "线程C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.decrement(0);
            }
        }, "线程D").start();

    }


    static class MyResouce { // 资源类
        int number = 0; // 数据
        private ReentrantLock lock = new ReentrantLock();
        private Condition condition = lock.newCondition();
        public void increment(int highlimit) {
            lock.lock();
            try {
                // 这里要用 while 判断，如果用 if 则会出现虚假唤醒，不能实现精确的先增后减
                //   虚假唤醒：多线程环境的编程中，我们经常遇到让多个线程等待在一个条件上，等到这个条件成立的时候我们再去唤醒这些线程，让它们接着往下执行代码的场景。
                //   假如某一时刻条件成立，所有的线程都被唤醒了，然后去竞争锁，因为同一时刻只会有一个线程能拿到锁，
                //   其他的线程都会阻塞到锁上无法往下执行，等到成功争抢到锁的线程消费完条件，释放了锁，后面的线程继续运行，拿到锁时这个条件很可能已经不满足了，
                //   这个时候线程应该继续在这个条件上阻塞下去，而不应该继续执行，如果继续执行了，就说发生了虚假唤醒。
                while (number >= highlimit) {
                    condition.await();
                }
                number ++;
                System.out.println(Thread.currentThread().getName() + "执行 + ，结果为 " + number);
                condition.signalAll();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void decrement(int lowlimit) {
            lock.lock();
            try {
                while (number == lowlimit) {
                    condition.await();
                }
                number--;
                System.out.println(Thread.currentThread().getName() + "执行 - ，结果为 " + number);
                condition.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
