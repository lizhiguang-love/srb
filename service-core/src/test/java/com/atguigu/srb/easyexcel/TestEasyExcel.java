package com.atguigu.srb.easyexcel;

import com.alibaba.excel.EasyExcel;
import net.sf.cglib.proxy.MethodInterceptor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestEasyExcel{
    @Test
    public void test(){
        String path="D:\\test\\";
        String filePath=path+"write.xlsx";
        EasyExcel.write(filePath,DemoData.class).sheet("模板").doWrite(data());
    }

    @Test
    public void test1(){

    }
    private List<DemoData> data() {
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }
}
