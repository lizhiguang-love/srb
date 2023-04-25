package com.atguigu.srb.collection;

public abstract class AbstractDemo {
    abstract int getNum(int a);
    public void test(){};

    static {
        System.out.println("1.加载父类静态代码块");
    }
    {
        System.out.println("3.加载父类非代码块");
    }
    protected AbstractDemo(){
        System.out.println("4.父类构造方法");
    }
}
