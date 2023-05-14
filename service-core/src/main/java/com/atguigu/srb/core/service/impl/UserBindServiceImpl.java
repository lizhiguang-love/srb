package com.atguigu.srb.core.service.impl;

import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.srb.core.enums.UserBindEnum;
import com.atguigu.srb.core.hfb.FormHelper;
import com.atguigu.srb.core.hfb.HfbConst;
import com.atguigu.srb.core.hfb.RequestHelper;
import com.atguigu.srb.core.mapper.UserAccountMapper;
import com.atguigu.srb.core.mapper.UserInfoMapper;
import com.atguigu.srb.core.pojo.entity.UserAccount;
import com.atguigu.srb.core.pojo.entity.UserBind;
import com.atguigu.srb.core.mapper.UserBindMapper;
import com.atguigu.srb.core.pojo.entity.UserInfo;
import com.atguigu.srb.core.pojo.vo.UserBindVO;
import com.atguigu.srb.core.service.UserBindService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务实现类
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Service
public class UserBindServiceImpl extends ServiceImpl<UserBindMapper, UserBind> implements UserBindService {

    @Resource
    private UserBindMapper userBindMapper;
    @Resource
    private UserInfoMapper userInfoMapper;
    @Resource
    private UserAccountMapper accountMapper;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public String commitBindUser(UserBindVO userBindVO, Long userId) {
        //不同userId,相同身份证
        QueryWrapper<UserBind> queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("user_id",userId)
                .eq("id_card",userBindVO.getIdCard());
        UserBind userBind = userBindMapper.selectOne(queryWrapper);
        Assert.isNull(userBind, ResponseEnum.USER_BIND_IDCARD_EXIST_ERROR);
        //用户是否曾经填写过绑定表单
        QueryWrapper<UserBind> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        userBind = userBindMapper.selectOne(wrapper);
        if (userBind !=null){
            BeanUtils.copyProperties(userBindVO,userBind);
            userBindMapper.updateById(userBind);
        }else {
            //创建用户绑定记录
            userBind = new UserBind();
            BeanUtils.copyProperties(userBindVO, userBind);
            userBind.setUserId(userId);
            userBind.setStatus(UserBindEnum.NO_BIND.getStatus());
            baseMapper.insert(userBind);
        }
        //组装自动提交表单的参数
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("agentId", HfbConst.AGENT_ID);
        paramMap.put("agentUserId", userId);
        paramMap.put("idCard",userBindVO.getIdCard());
        paramMap.put("personalName", userBindVO.getName());
        paramMap.put("bankType", userBindVO.getBankType());
        paramMap.put("bankNo", userBindVO.getBankNo());
        paramMap.put("mobile", userBindVO.getMobile());
        paramMap.put("returnUrl", HfbConst.USERBIND_RETURN_URL);
        paramMap.put("notifyUrl", HfbConst.USERBIND_NOTIFY_URL);
        paramMap.put("timestamp", RequestHelper.getTimestamp());
        paramMap.put("sign", RequestHelper.getSign(paramMap));

        //生成动态表单字符串
        String formStr = FormHelper.buildForm(HfbConst.USERBIND_URL, paramMap);
        return formStr;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void callback(Map<String, Object> map) {
        String userId = (String) map.get("agentUserId");
        String bindCode = (String) map.get("bindCode");
        //更新userbind账户数据
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id",userId);
        UserBind userBind = userBindMapper.selectOne(userBindQueryWrapper);

        userBind.setBindCode(bindCode);
        userBind.setStatus(UserBindEnum.BIND_OK.getStatus());
        userBindMapper.updateById(userBind);
        //更新用户表
        //获取手机号,因为手机号在user_info表中是唯一存在的，一个手机号只会被注册一次
        String mobile = userBind.getMobile();
        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);
        UserInfo userInfo = userInfoMapper.selectOne(wrapper);
        userInfo.setBindCode(bindCode);
        userInfo.setName(userBind.getName());
        userInfo.setIdCard(userBind.getIdCard());
        userInfo.setBindStatus(UserBindEnum.BIND_OK.getStatus());
        userInfoMapper.updateById(userInfo);
        //在user_account表中新增用户信息
        QueryWrapper<UserAccount> accountQueryWrapper = new QueryWrapper<>();
        accountQueryWrapper.eq("user_id",userId);
        UserAccount userAccount = accountMapper.selectOne(accountQueryWrapper);
        if (userAccount == null){
            UserAccount account = new UserAccount();
            account.setUserId(Long.valueOf(userId));
            account.setAmount(new BigDecimal(0));
            account.setFreezeAmount(new BigDecimal(0));
            accountMapper.insert(account);
        }
    }

    @Override
    public String getBindCodeByUserId(Long userId) {
        QueryWrapper<UserBind> userBindQueryWrapper = new QueryWrapper<>();
        userBindQueryWrapper.eq("user_id", userId);
        UserBind userBind = baseMapper.selectOne(userBindQueryWrapper);
        return userBind.getBindCode();
    }


}
