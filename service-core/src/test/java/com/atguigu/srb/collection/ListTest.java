package com.atguigu.srb.collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class ListTest {
    public static void main(String[] args) {
        HashMap<Object, Object> map = new HashMap<>();
        Vector<Object> objects = new Vector<>();
        Hashtable<Object, Object> hashtable = new Hashtable<>();
        hashtable.put("1",2);
        System.out.println("呵呵---》"+hashtable.get("1"));
        ConcurrentHashMap<Object, Object> objectObjectConcurrentHashMap = new ConcurrentHashMap<>();
        map.put(null,null);
        List list = new ArrayList<>();
        LinkedList linkedList = new LinkedList();
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        Iterator iterator = list.iterator();
        while (iterator.hasNext()){
            int next = (int)iterator.next();
            System.out.println(next);
            iterator.remove();
        }
        System.out.println(list.isEmpty());
        for (int i = 1; i < 10; i++) {
            for (int j=1;j<=i;j++){
                System.out.print(i + "*" + j + "=" +(i*j)+" ");

            }
            System.out.println("");
        }
    }
}
