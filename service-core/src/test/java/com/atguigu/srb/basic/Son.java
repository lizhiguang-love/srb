package com.atguigu.srb.basic;

public class Son extends Person{
    int i=5;
    float num=11.1f;
    double dou=11.1;
    void test(){
        System.out.println("i="+i);
    }

    public static void main(String[] args) {
        Son son = new Son();
        son.i=10;
        son.test();
        Person person=new Son();
        person.test();

    }
}
