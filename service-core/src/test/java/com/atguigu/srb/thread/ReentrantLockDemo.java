package com.atguigu.srb.thread;

import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockDemo {
    public static void main(String[] args) {
        ReentrantLock reentrantLock = new ReentrantLock();
        try{
            reentrantLock.lock();
            boolean locked = reentrantLock.isLocked();
            new Thread(()->{
                System.out.println("呵呵"+locked);
            }).start();
        }catch (Exception e){

        }finally {
            reentrantLock.unlock();
        }

    }

}
