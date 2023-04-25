package com.atguigu.srb.collection;

import java.util.concurrent.TimeUnit;

public class PrintABTest extends Thread{
    // 判断上一次打印是否是打印的字符 A。
    private boolean flag = false;

    /**
     * 打印字符 A 的方法
     */
    public synchronized void printA(){
        try {
            // 判断上一次打印是否是打印的 AA，如果是就进行等待，如果不是就执行下面的代码。
            while (flag){
                wait();
            }
            System.out.print("AA");
            flag = true;
            // 唤醒在等待的线程
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * 打印字符 B 的方法
     */
    public synchronized void printB(){
        try{
            // 判断上一次打印是否是打印的 B，如果是就进行等待，如果不是就执行下面的代码。
            // 这里是去反，因为上次打印如果不是A，肯定就是B。
            while (!flag){
                wait();
            }
            System.out.print("B");
            flag = false;
            // 唤醒在等待的线程
            notifyAll();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * main 方法
     * 启动线程调用打印方法
     */
    public static void main(String[] args) {
        // 创建实例
        PrintABTest printABTest = new PrintABTest();
        for (int i = 0; i < 30; i++) {
            // 打印 A
            new Thread(()->{
                printABTest.printA();
            }, "printA").start();

            // 打印 B
            new Thread(()->{
                printABTest.printB();
            },"printB").start();
        }
    }
}
