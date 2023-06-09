package com.atguigu.srb.core.controller.api;


import com.alibaba.fastjson.JSON;
import com.atguigu.common.result.R;
import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.hfb.RequestHelper;
import com.atguigu.srb.core.pojo.vo.UserBindVO;
import com.atguigu.srb.core.service.UserBindService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 前端控制器
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Api(tags = "会员账号绑定")
@RestController
@RequestMapping("/api/core/userBind")
@Slf4j
public class UserBindController {
    @Resource
    private UserBindService userBindService;

    @ApiOperation("账户绑定提交数据")
    @PostMapping("/auth/bind")
    public R bind(@RequestBody UserBindVO userBindVO, HttpServletRequest request){

        //从header中获取token，并对token进行校验，确保用户已登录，并从token中提取userId
        String token = request.getHeader("token");
        Long userId = JwtUtils.getUserId(token);
        log.info("user_id--->"+userId);
        //根据userId做账户绑定，生成一个动态表单的字符串
        String formStr = userBindService.commitBindUser(userBindVO, userId);
        return R.ok().data("formStr", formStr);
    }
    @ApiOperation("账号绑定异步回调")
    @PostMapping("/notify")
    public String notify(HttpServletRequest request){
        System.out.println("进入到方法中了");
        Map<String, Object> paramMap = RequestHelper.switchMap(request.getParameterMap());
        log.info("异步回调接口参数--->"+ JSON.toJSONString(paramMap));
        //先校验签名确认用户是否被修改
        boolean result = RequestHelper.isSignEquals(paramMap);
        if (result){
            userBindService.callback(paramMap);
        }else {
            log.error("用户账号绑定异步回调签名验证错误：" + JSON.toJSONString(paramMap));
            return "fail";
        }
        return "success";
    }
}

