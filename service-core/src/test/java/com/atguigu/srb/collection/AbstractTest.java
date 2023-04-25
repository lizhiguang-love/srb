package com.atguigu.srb.collection;

public class AbstractTest extends AbstractDemo {
    int b=10;
    static {
        System.out.println("2.加载子类静态代码块");
    }
    @Override
    int getNum(int a) {
        return 0;
    }

    @Override
    public void test() {
        super.test();
    }
    {
        System.out.println("5.子类非静态代码块");
    }
    public AbstractTest(){
        super();
        System.out.println("6.子类构造方法");
    }
    public static void main(String[] args) {
        AbstractTest test = new AbstractTest();
    }
}
