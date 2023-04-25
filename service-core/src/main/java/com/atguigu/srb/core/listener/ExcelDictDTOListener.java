package com.atguigu.srb.core.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.srb.core.mapper.DictMapper;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ExcelDictDTOListener extends AnalysisEventListener<ExcelDictDTO> {
    private DictMapper dictMapper;
    //数据列表
    private List<ExcelDictDTO> list =  new ArrayList<>();

    //每个5条记录批量存储一次数据
    private static final int BATCH_COUNT = 5;
    public ExcelDictDTOListener(DictMapper baseMapper){
        this.dictMapper=baseMapper;
    }
    @Override
    public void invoke(ExcelDictDTO excelDictDTO, AnalysisContext analysisContext) {
        list.add(excelDictDTO);
        if (list.size()>=BATCH_COUNT){
            int result = dictMapper.insertBatch(list);
            if (result != 0){
                list.clear();
            }
        }
        log.info("读取数据-->"+excelDictDTO);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //当excel表的数据不足5条时，把剩下的数据添加到库中
        dictMapper.insertBatch(list);
        log.info("数据读取完成");
    }
}
