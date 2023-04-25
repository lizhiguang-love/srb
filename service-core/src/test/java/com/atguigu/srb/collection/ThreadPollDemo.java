package com.atguigu.srb.collection;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPollDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor();
        ReentrantLock reentrantLock = new ReentrantLock();
        byte b=1;
        int count=0;
        while (++b>0){
            System.out.println(b);
            count++;

        }
        System.out.println(count);
    }

    // 该变量可以理解成：上一次打印是否是打印的字符 A。
    private volatile boolean flag = false;

    /**
     * 打印字符 A 的方法
     */
    private synchronized void printA(){
        try {
            // 判断上一次打印是否是打印的 A，如果是就进行等待，如果不是就执行下面的代码。
            while (flag){
                wait();
            }
            System.out.println("A");
            flag = true;
            // 唤醒在等待的线程
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
