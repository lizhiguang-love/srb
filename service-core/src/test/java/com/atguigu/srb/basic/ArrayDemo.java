package com.atguigu.srb.basic;

import org.junit.Test;

public class ArrayDemo {
    @Test
    public void test1(){
        int[] array=new int[5];
        array[1]=5;
        array[4]=6;
        String[] str=new String[2];
        System.out.println(str[1]);
    }

    @Test
    public void test2(){
        int[] array={6,2,5,3,1};
        int temp;
        //冒泡排序：外层控制的是循环的次数，内层进行排序
        for (int i=0;i<array.length-1;i++){
            for (int j=0;j<array.length-1;j++){
                if (array[j]>array[j+1]){
                    temp=array[j];
                    array[j]=array[j+1];
                    array[j+1]=temp;
                }
            }
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }

    @Test
    public void test3(){
        int[] array={6,2,5,3,1};
        int temp;
        //选择排序
        for (int i=0;i<array.length-1;i++){
            for (int j=1+i;j<array.length;j++){
                if (array[i]>array[j]){
                    temp=array[j];
                    array[j]=array[i];
                    array[i]=temp;
                }
            }
        }
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
    @Test
    public void test4(){
        int j=1;
        switch (4){
            case 1:j++;
            case 2:j++;
            case 3:j++;
            case 4:--j;
        }
        System.out.println(j);
    }
    @Test
    public void test5(){
        int i=1,j=10;
        do {
            if(i++ > --j)continue;;
        }while (i<5);
        System.out.println(i+"==="+j);
    }
    @Test
    public void test6(){
        int a=4;
        int b=3;
//       if ((a-b) &&(b=b-5)||(a=b)){
//           --a;
//       }
    }
}
