package com.atguigu.srb.core.pojo.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.Api;
import lombok.Data;
@Api("excel表格对象")
@Data
public class ExcelDictDTO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("上级id")
    private Long parentId;

    @ExcelProperty("名称")
    private String name;

    @ExcelProperty("值")
    private Integer value;

    @ExcelProperty("编码")
    private String dictCode;
}