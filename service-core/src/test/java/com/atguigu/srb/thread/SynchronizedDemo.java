package com.atguigu.srb.thread;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.标准访问有a,b两个线程,先打印邮件还是先打印短信   邮件
 * 2.sendEmail方法中加入暂停3秒钟，先打印邮件还是短信   邮件
 * 3.添加一个Hello方法，先打印邮件还是hello   hello
 * 4.有两部手机，先打印邮件还是短信  短信
 * 5.有两个静态同步方法，有1部手机，先打印邮件还是短信
 * 6.有两个静态同步方法，有2部手机，先打印邮件还是短信
 * 7.有一个静态同步方法，有一个普通同步方法，有1部手机，先打印邮件还是短信
 * 8.有一个静态同步方法，有一个普通同步方法，有2部手机，先打印邮件还是短信
 */
public class SynchronizedDemo {
    public static void main(String[] args) throws InterruptedException {
        Phone phone1 = new Phone();
        Phone phone2 = new Phone();
        ReentrantLock reentrantLock = new ReentrantLock();
        new Thread(()->{
            reentrantLock.lock();
            phone1.sendEmail();
            boolean locked = reentrantLock.isLocked();
            System.out.println(locked);
            reentrantLock.unlock();
        },"a").start();


        TimeUnit.MILLISECONDS.sleep(1000);

//        new Thread(()->{
////            phone1.hello();
//            phone1.sendSMS();
//        },"c").start();

        synchronized (phone1){
            System.out.println("呵呵");
        }
    }

}
class Phone{
    public  void sendEmail() {
        try {
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("------sendEmail");
    }
    public  void sendSMS(){
        System.out.println("--------sendSMS");
    }
    public static void hello(){
        System.out.println("------hello");
    }

}
