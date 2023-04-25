package com.atguigu.srb.thread;

import java.util.concurrent.*;

public class CompletableFutureDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        CompletableFuture.supplyAsync(()->{
            int result = ThreadLocalRandom.current().nextInt();
            System.out.println("随机数——————》"+result);
            return result;
        },threadPool);
    }
}
