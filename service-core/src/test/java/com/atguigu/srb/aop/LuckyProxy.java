package com.atguigu.srb.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LuckyProxy implements InvocationHandler {
    public Object object;
    public LuckyProxy(Object object){
        this.object=object;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(object,args);
        System.out.println("****打印日志****");

        return null;
    }

    public Object getLuckyProxy(Object object){
        return Proxy.newProxyInstance(object.getClass().getClassLoader(),object.getClass().getInterfaces(),this);
    }
}
