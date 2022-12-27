package multithreading.code01;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 实现停车场功能,有100辆车需要到停车场停车，但只有10个停车位
 *
 * acquire()
 * 获取一个令牌，在获取到令牌、或者被其他线程调用中断之前线程一直处于阻塞状态。
 * ​
 * acquire(int permits)
 * 获取一个令牌，在获取到令牌、或者被其他线程调用中断、或超时之前线程一直处于阻塞状态。
 *
 * acquireUninterruptibly()
 * 获取一个令牌，在获取到令牌之前线程一直处于阻塞状态（忽略中断）。
 *
 * tryAcquire()
 * 尝试获得令牌，返回获取令牌成功或失败，不阻塞线程。
 * ​
 * tryAcquire(long timeout, TimeUnit unit)
 * 尝试获得令牌，在超时时间内循环尝试获取，直到尝试获取成功或超时返回，不阻塞线程。
 * ​
 * release()
 * 释放一个令牌，唤醒一个获取令牌不成功的阻塞线程。
 * ​
 * hasQueuedThreads()
 * 等待队列里是否还存在等待线程。
 * ​
 * getQueueLength()
 * 获取等待队列里阻塞的线程数。
 * ​
 * drainPermits()
 * 清空令牌把可用令牌数置为0，返回清空令牌的数量。
 * ​
 * availablePermits()
 * 返回可用的令牌数量。
 */
public class Park {

    static Semaphore s = new Semaphore(10);

    public static void main(String[] args) {
        for(int i = 0; i < 20; i++) {
            new Thread(() -> {
                try {
                    if(s.availablePermits()==0){
                        System.out.println(Thread.currentThread().getName() + "车位不足");
                    }
                    System.out.println(Thread.currentThread().getName() + "开始停车");
                    s.acquire();
                    int i1 = new Random().nextInt(10);

                    System.out.println(Thread.currentThread().getName() + "停" + i1 + "秒" );
                    TimeUnit.SECONDS.sleep(i1);
                    s.release();
                    System.out.println(Thread.currentThread().getName() + "停车完毕");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, i+"号车").start();
        }
    }
}

