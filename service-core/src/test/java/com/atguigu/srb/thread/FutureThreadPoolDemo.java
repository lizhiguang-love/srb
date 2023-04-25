package com.atguigu.srb.thread;

import java.util.concurrent.*;

public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            System.out.println("********线程1*******");
            TimeUnit.MILLISECONDS.sleep(300);
            return "task1 over";
        });
        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            System.out.println("********线程2*******");
            TimeUnit.MILLISECONDS.sleep(500);
            return "task2 over";
        });
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //并行任务
        threadPool.execute(futureTask1);
        threadPool.execute(futureTask2);
        threadPool.submit(futureTask1);
        threadPool.shutdown();
    }

}
