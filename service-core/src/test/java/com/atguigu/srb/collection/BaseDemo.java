package com.atguigu.srb.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class  BaseDemo {
    String str;
    volatile int c=1;

    @Test
    public void test1(){
        int i=0;
        int a=i++;
        int c;
        System.out.println("a1="+a+"  i="+i);
        int b=++a;
        int d;
        System.out.println("a2="+a+"  b="+b);
        System.out.println(str);
    }

    @Test
    public void test2() throws InterruptedException {
        int count=0;
        new Thread(()->{
            c--;
        },"t1").start();

        while (c>0){
            System.out.println("呵呵"+c);
            count++;
        }
        System.out.println(count);
        synchronized (this){

        }
    }
    @Test
    public void test3(){
        String str="sdfd1545f#*( d";
        char[] chars = str.toCharArray();
        HashMap<String,Integer> map = new HashMap<>();
        int value=0;
        int values=0;
        for (char aChar : chars) {
            if ((aChar>='a' && aChar<='z') ||(aChar>='A' && aChar<='Z')){
                value++;
            }else if (aChar>='0' && aChar<='9'){
                values++;
            }
        }
        map.put("test",value);
        map.put("values",values);
        System.out.println(map.get("values"));
        int[] ints = new int[10];
        System.out.println(ints[9]);
    }
    @Test
    public void test4(){
        List list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
        }
        System.out.println(list.get(0));
    }
}
