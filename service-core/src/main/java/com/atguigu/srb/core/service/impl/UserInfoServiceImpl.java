package com.atguigu.srb.core.service.impl;

import com.atguigu.common.exception.Assert;
import com.atguigu.common.result.ResponseEnum;
import com.atguigu.common.util.MD5;
import com.atguigu.srb.base.util.JwtUtils;
import com.atguigu.srb.core.mapper.UserAccountMapper;
import com.atguigu.srb.core.mapper.UserLoginRecordMapper;
import com.atguigu.srb.core.pojo.entity.UserAccount;
import com.atguigu.srb.core.pojo.entity.UserInfo;
import com.atguigu.srb.core.mapper.UserInfoMapper;
import com.atguigu.srb.core.pojo.entity.UserLoginRecord;
import com.atguigu.srb.core.pojo.query.UserInfoQuery;
import com.atguigu.srb.core.pojo.vo.LoginVO;
import com.atguigu.srb.core.pojo.vo.RegisterVO;
import com.atguigu.srb.core.pojo.vo.UserInfoVO;
import com.atguigu.srb.core.service.UserInfoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 用户基本信息 服务实现类
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private UserAccountMapper userAccountMapper;

    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;

    @Resource(name = "threadPoolTaskExecutor")
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Resource
    private RedisTemplate redisTemplate;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void register(RegisterVO registerVO) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",registerVO.getMobile());
        Integer count = userInfoMapper.selectCount(queryWrapper);
        Assert.equals(count,0, ResponseEnum.MOBILE_EXIST_ERROR);
        //插入用户信息记录：user_info
        UserInfo userInfo = new UserInfo();
        userInfo.setUserType(registerVO.getUserType());
        userInfo.setNickName(registerVO.getMobile());
        userInfo.setName(registerVO.getMobile());
        userInfo.setMobile(registerVO.getMobile());
        userInfo.setPassword(MD5.encrypt(registerVO.getPassword()));
        userInfo.setStatus(UserInfo.STATUS_NORMAL);
        userInfo.setHeadImg(UserInfo.USER_AVATAR);
        userInfoMapper.insert(userInfo);

        //插入用户账户记录：user_account
        UserAccount userAccount = new UserAccount();
        userAccount.setUserId(userInfo.getId());
        userAccountMapper.insert(userAccount);
    }

    @Override
    public UserInfoVO login(LoginVO loginVO,String ip) {
        String uuid = UUID.randomUUID().toString();
        ValueOperations<String,String> ops = redisTemplate.opsForValue();
        Boolean lock = ops.setIfAbsent("lock", uuid, 5, TimeUnit.SECONDS);

        if (lock){
            String lockValue = ops.get("lock");
            String script = "if redis.call(\"get\",KEYS[1]) == ARGV[1] then\n" +
                    "    return redis.call(\"del\",KEYS[1])\n" +
                    "else\n" +
                    "    return 0\n" +
                    "end";
            redisTemplate.execute(new DefaultRedisScript<Long>(script,Long.class), Arrays.asList("lock"),lockValue);
            String password = loginVO.getPassword();
            String mobile = loginVO.getMobile();
            Integer userType = loginVO.getUserType();
            //判断用户是否存在
            QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("user_type",loginVO.getUserType())
                    .eq("mobile",mobile);
            UserInfo userInfo = userInfoMapper.selectOne(queryWrapper);
            Assert.notNull(userInfo, ResponseEnum.LOGIN_MOBILE_ERROR);
            //密码是否正确
            Assert.equals(MD5.encrypt(password), userInfo.getPassword(), ResponseEnum.LOGIN_PASSWORD_ERROR);
            //用户是否被禁用
            Assert.equals(userInfo.getStatus(), UserInfo.STATUS_NORMAL, ResponseEnum.LOGIN_LOKED_ERROR);
            //生成token
            String token = JwtUtils.createToken(userInfo.getId(), userInfo.getName());
            //组装UserInfoVO
            UserInfoVO userInfoVO = new UserInfoVO();
            userInfoVO.setToken(token);
            userInfoVO.setName(userInfo.getName());
            userInfoVO.setNickName(userInfo.getNickName());
            userInfoVO.setHeadImg(userInfo.getHeadImg());
            userInfoVO.setMobile(mobile);
            userInfoVO.setUserType(userType);
            //记录登录日志
            UserLoginRecord userLoginRecord = new UserLoginRecord();
            userLoginRecord.setUserId(userInfo.getId());
            userLoginRecord.setIp(ip);
            userLoginRecordMapper.insert(userLoginRecord);
            return userInfoVO;
        }



        return null;
    }
//    @Async("threadPoolTaskExecutor")


    @Override
    public IPage<UserInfo> listPage(long page,long limit, UserInfoQuery userInfoQuery) {

//        if(userInfoQuery == null){
//            return baseMapper.selectPage(pageParam, null);
//        }
        String mobile = userInfoQuery.getMobile();
        Integer status = userInfoQuery.getStatus();
        Integer userType = userInfoQuery.getUserType();

        QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();
        userInfoQueryWrapper
                .eq(StringUtils.isNotBlank(mobile), "mobile", mobile)
                .eq(status != null, "status", status)
                .eq(userType != null, "user_type", userType);
        Page<UserInfo> pageParam = new Page<>(page, limit);
        Page<UserInfo> userInfoPage = baseMapper.selectPage(pageParam, userInfoQueryWrapper);

        return userInfoPage;

    }

    @Override
    public void lock(long id, Integer status) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(id);
        userInfo.setStatus(status);
        baseMapper.updateById(userInfo);
    }

    @Override
    public boolean checkMobile(String mobile) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile",mobile);
        Integer count = userInfoMapper.selectCount(queryWrapper);
        return count>0;
    }

    @Override
    public List<UserInfo> selectUser(int pageSize, int pageNum) {
//        List<UserInfo> userInfoList = userInfoMapper.selectUser(2, 2);

        return null;
    }
}
