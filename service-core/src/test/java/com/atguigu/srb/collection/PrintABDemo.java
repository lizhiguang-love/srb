package com.atguigu.srb.collection;

public class PrintABDemo {
    /**
     * main 方法
     * 启动线程调用打印方法
     */
    public static void main(String[] args) {
        // 创建实例
        PrintABTest printABTest = new PrintABTest();
        for (int i = 0; i < 30; i++) {
            // 打印 A
//            new Thread(printABTest::printA).start();
            new Thread(()->{
                printABTest.printA();
            }, "printA").start();

            // 打印 B
//            new Thread(printABTest::printB).start();
            new Thread(()->{
                printABTest.printB();
            },"printB").start();
        }
    }
}
