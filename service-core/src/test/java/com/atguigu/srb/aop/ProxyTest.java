package com.atguigu.srb.aop;

public class ProxyTest {
    public static void main(String[] args) {
        ProxyObject proxyObject = new ProxyObject();
        LuckyProxy luckyProxy = new LuckyProxy(proxyObject);
        princl proxy = (princl)luckyProxy.getLuckyProxy(proxyObject);
        proxy.test();
    }
}
