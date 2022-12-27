package multithreading.code01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 子进程先执行三次，主进程再执行五次，然后这个过程执行三次
 * 诀窍：
 * 1. 在资源类内部定义一个控制变量flag，flag为true主线程挂起，默认先让子线程执行
 */
public class MainFuc {
    public static void main(String[] args) {
        MainFunctin mainFunctin = new MainFunctin();
        new Thread(() -> {
            for (int i=0;i<3;i++){
                mainFunctin.SubFunction();
            }
        }).start();
        new Thread(() -> {
            for (int i=0;i<3;i++){
                mainFunctin.MainFunction();
                System.out.println("------------------------");
            }
        }).start();

    }
}
// 定义一个外部类
class MainFunctin{
    private boolean flag = true;
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    public void MainFunction(){
        try {
            lock.lock();
            while (true){
                if (flag){
                    condition.await();
                }else {
                    for (int i=0;i<5;i++){
                        System.out.println("主线程执行："+i);
                    }
                    condition.signal();
                    this.flag=!flag;
                    break;
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }
    public void SubFunction(){
        try {
            lock.lock();
            while (true){
                if (!flag){
                    condition.await();
                }else {
                    for (int i =0;i<3;i++){
                        System.out.println("子线程执行："+i);
                    }
                    condition.signal();
                    this.flag=!flag;
                    break;
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}

