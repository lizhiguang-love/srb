package com.atguigu.srb.core.controller.admin;

import com.atguigu.common.result.R;
import com.atguigu.srb.core.pojo.entity.IntegralGrade;
import com.atguigu.srb.core.service.IntegralGradeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
@RestController
@RequestMapping("/admin/core/integralGrade")
public class AdminIntegralGradeController {
    @Resource
    private IntegralGradeService integralGradeService;

    @ApiOperation("查询积分等级列表")
    @GetMapping("/list")
    public R listAll(){
        List<IntegralGrade> list = integralGradeService.list();
        return R.ok().data("list",list).message("查询积分等级列表成功");
    }
    @ApiOperation(("根据id删除积分等级"))
    @DeleteMapping("/remove/{id}")
    public R removeById(@PathVariable Long id){
        boolean result = integralGradeService.removeById(id);
        if (result){
            return R.ok().message("删除成功");
        }else {
            return R.error().message("删除失败");
        }
    }
    @PostMapping("/save")
    public R save(@RequestBody IntegralGrade integralGrade){
        boolean result = integralGradeService.save(integralGrade);
        if (result){
            return R.ok().message("添加成功");
        }else {
            return R.error().message("添加失败");
        }
    }
    @PutMapping("/update")
    public R update(@RequestBody IntegralGrade integralGrade){
        boolean result = integralGradeService.updateById(integralGrade);
        if (result){
            return R.ok().message("修改成功");
        }else {
            return R.error().message("修改失败");
        }
    }
    @GetMapping("/get/{id}")
    public R getById(@PathVariable long id){
        IntegralGrade integralGrade = integralGradeService.getById(id);
        if (integralGrade!=null){
            return R.ok().data("integralGrade",integralGrade).message("查询成功");
        }else {
            return R.ok().message("查询失败");
        }
    }
}
