package com.atguigu.srb.collection;

public class Test {
    public static void main(String[] args) {
        SumTest sumTest = new SumTest();
        System.out.println(sumTest.sum(1,2));
        AbstractTest abstractTest = new AbstractTest();
        System.out.println(abstractTest.getNum(10));
    }
}
class SumTest{
    public int sum(int a,int b){
        return a+b;
    }
}