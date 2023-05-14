package com.atguigu.srb.thread;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 1.需求：
 *      1.1 同一款产品，同时搜索出在各大电商平台的售价
 *      1.2 同一款产品，同时搜索出在同一个电商平台下，各个入驻卖家售价是多少
 * 2.输出 结果希望是同款产品的不同地方的价格清单列表 返回一个List<String></>
 */
public class CompletableFutureMallDemo {
    //模拟电商平台
    static List<NetMall> list= Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("tabobao")
    );

    /**
     *
     * @param list
     * @param productName 产品名
     * @return
     */
    public static List<String> getPrice(List<NetMall> list,String productName){
        ArrayList<String> arrayList = new ArrayList<>();
        for (NetMall netMall : list) {
            arrayList.add("平台："+netMall.getMallName()+"  价格："+netMall.callPrice(productName));
        }
        return arrayList;
    }
    public static void main(String[] args) {
        long stratTime = System.currentTimeMillis();
        List<String> stringList = getPrice(list, "mysql");
        for (String s : stringList) {
            System.out.println(s);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("花费时间--->"+(endTime-stratTime)+"毫秒");
        System.out.println("-----------------");
        long stratTimes = System.currentTimeMillis();
        List<String> future = getPriceByCompletableFuture(list, "mysql");
        for (String s : future) {
            System.out.println(s);
        }
        long endTimes = System.currentTimeMillis();
        System.out.println("花费时间--->"+(endTimes-stratTimes)+"毫秒");
    }
    public static List<String> getPriceByCompletableFuture(List<NetMall> list,String productName){
        ArrayList<String> arrayList = new ArrayList<>();
        List<CompletableFuture<String>> list1=new ArrayList<>();
        List<FutureTask<String>> list2=new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (NetMall netMall : list) {
//            CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
//                String str = "平台：" + netMall.getMallName() + "  价格：" + netMall.callPrice(productName);
//                return str;
//            });
            FutureTask<String> futureTask = new FutureTask(() -> {
                String str = "平台：" + netMall.getMallName() + "  价格：" + netMall.callPrice(productName);
                return str;
            });
            executorService.execute(futureTask);
//            list1.add(completableFuture);
            list2.add(futureTask);
        }
//        for (CompletableFuture<String> stringCompletableFuture : list1) {
//            arrayList.add(stringCompletableFuture.join());
//        }
        for (FutureTask<String> stringFutureTask : list2) {
            try {
                arrayList.add(stringFutureTask.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return arrayList;

    }
}
 class NetMall{
    @Getter
    private String mallName;
    public NetMall(String mallName){
        this.mallName=mallName;
    }
    public double callPrice(String productName){
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ThreadLocalRandom.current().nextDouble()*2+productName.charAt(0);
    }
}
