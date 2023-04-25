package com.atguigu.srb.sms.controller.api;

import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.R;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.common.util.RandomUtils;
import com.atguigu.srb.sms.client.CoreUserInfoClient;
import com.atguigu.srb.sms.service.SmsService;
import com.atguigu.srb.sms.util.SmsProperties;
import io.swagger.annotations.Api;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@RequestMapping("/api/sms")
@RestController
@Api(tags = "短信管理")
public class ApiSmsController {

    @Resource
    private SmsService smsService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private CoreUserInfoClient coreUserInfoClient;
    @GetMapping("/send/{mobile}")
    public R send(@PathVariable String mobile){
        //校验手机号码不能为空
        Assert.notEmpty(mobile, ResponseEnum.MOBILE_NULL_ERROR);
        //校验手机号是否被注册
        boolean result = coreUserInfoClient.checkMobile(mobile);
        Assert.isTrue(result==false,ResponseEnum.MOBILE_EXIST_ERROR);
        String code = RandomUtils.getFourBitRandom();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code",code);
        smsService.send(mobile, SmsProperties.TEMPLATE_CODE,map);
        System.out.printf("验证码--->"+code);
        //将验证码放入到redis中
        redisTemplate.opsForValue().set("srb:sms:code"+mobile,code,5, TimeUnit.MINUTES);
        return R.ok().message("短信发送成功");
    }
}
