package com.atguigu.srb.core.controller.admin;


import com.atguigu.common.result.R;
import com.atguigu.srb.core.config.ThreadPoolConfig;
import com.atguigu.srb.core.pojo.entity.UserInfo;
import com.atguigu.srb.core.pojo.query.UserInfoQuery;
import com.atguigu.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * <p>
 * 用户基本信息 前端控制器
 * </p>
 *
 * @author lizhiguang
 * @since 2022-08-06
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping("/admin/core/userInfo")
@Slf4j
//@CrossOrigin  gateway网关中已配置跨域访问
public class AdminUserInfoController {

    @Resource
    private UserInfoService userInfoService;

    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @ApiOperation("获取会员分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页码", required = true)
            @PathVariable long page,
            @ApiParam(value = "每页记录数", required = true)
            @PathVariable long limit,
            @ApiParam(value = "查询对象", required = false)
            UserInfoQuery userInfoQuery

    ) {
        IPage<UserInfo> pageModel = userInfoService.listPage(page,limit, userInfoQuery);
        return R.ok().data("pageModel", pageModel);
    }
    @ApiOperation("锁定和解锁")
    @PutMapping("/lock/{id}/{status}")
    public R lock(
            @ApiParam(value = "用户id", required = true)
            @PathVariable("id") long id,

            @ApiParam(value = "锁定状态（0：锁定 1：正常）", required = true)
            @PathVariable("status") Integer status){

        userInfoService.lock(id, status);
        return R.ok().message(status==1?"解锁成功":"锁定成功");
    }

    @ApiOperation("校验手机号")
    @GetMapping("/checkMobile/{mobile}")
    public boolean checkMobile(@PathVariable String mobile){
        boolean result = userInfoService.checkMobile(mobile);
        return result;
    }
}

