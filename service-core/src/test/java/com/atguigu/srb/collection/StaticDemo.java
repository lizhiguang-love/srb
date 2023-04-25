package com.atguigu.srb.collection;

public class StaticDemo {
    static int cnt=6;
    static {
        cnt/=3;
    }
    static {
        cnt+=9;
    }


    public static void main(String[] args) {
        System.out.println("cnt="+cnt);
        int a=10;
        double b=3.14;
        System.out.println('A'+a+b);
    }

}
