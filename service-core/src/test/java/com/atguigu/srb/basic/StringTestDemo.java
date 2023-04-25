package com.atguigu.srb.basic;

public class StringTestDemo {
    public static void main(String[] args) {
        String str1="abc";
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("abc");
        StringBuffer stringBuffer1=new StringBuffer("abc");
        String str2="ab"+"c";
        String str3=new String("abc");
        System.out.println(str1==str3);//false
        System.out.println(stringBuffer.equals(str1));//false
        System.out.println(stringBuffer==stringBuffer1);//false
        System.out.println(stringBuffer.equals(stringBuffer1));//false
    }
}
