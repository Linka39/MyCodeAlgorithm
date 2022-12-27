package multithreading.code01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TVShow {
    /**
     * 题目：艺术表演节目(信号灯法),同一时刻只能有一个表演者和观看者，并且打印表演的节目
     *
     * 诀窍：
     * 1. 高内聚低耦合的前提下，线程操作资源类
     * 2. 判断 、干活、通知
     * 3. 多线程交互中，必须要防止多线程的虚假唤醒，也即（判断不能用if，只能用while）
     */
    public static void main(String[] args) {
        TV tv = new TVShow().new TV();
        Player player = new TVShow().new Player(tv);
        Watcher watcher = new TVShow().new Watcher(tv);
        new Thread(player).start();
        new Thread(watcher).start();
    }
    // 定义三个内部类实现
    class Player implements Runnable{
        private TV tv;
        public Player(TV tv){
            this.tv = tv;
        }
        @Override
        public void run() {
            for (int i=0;i<5;i++){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv.play("红楼梦");
            }
        }
    }
    class Watcher implements Runnable{
        private TV tv;
        public Watcher(TV tv){
            this.tv = tv;
        }
        @Override
        public void run() {
            for (int i=0;i<5;i++){
                tv.watch();
            }
        }
    }
    class TV{
        private boolean flag = true;
        private final ReentrantLock lock = new ReentrantLock();
        private final Condition condition = lock.newCondition();
        private String program;
        public void play(String program){
            try {
                lock.lock();
                if (!flag){
                    condition.await();
                }
                System.out.println("表演了-"+program);
                condition.signal();
                this.program=program;
                this.flag=!flag;
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
        public void watch(){
            try {
                lock.lock();
                if (flag){
                    lock.lock();
                    condition.await();
                }
                System.out.println("观看了-"+program);
                condition.signal();
                this.flag=!flag;
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }

        }
    }
}
