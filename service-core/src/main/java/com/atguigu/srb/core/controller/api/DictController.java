package com.atguigu.srb.core.controller.api;


import com.atguigu.common.result.R;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Api(tags = "数据字典")
@RestController
@RequestMapping("/api/core/dict")
@Slf4j
public class DictController {
    @Autowired
    private DictService dictService;
    @ApiOperation("根据dictCode获取下级节点")
    @GetMapping("/findByDictCode/{dictCode}")
    public R findByDictCode(@PathVariable String dictCode){
        List<Dict> dictList = dictService.findByDictCode(dictCode);
        return R.ok().data("dictList",dictList);
    }
}

