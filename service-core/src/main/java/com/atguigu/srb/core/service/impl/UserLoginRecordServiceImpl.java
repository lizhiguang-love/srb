package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.core.pojo.entity.UserLoginRecord;
import com.atguigu.srb.core.mapper.UserLoginRecordMapper;
import com.atguigu.srb.core.service.UserLoginRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户登录记录表 服务实现类
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Service
public class UserLoginRecordServiceImpl extends ServiceImpl<UserLoginRecordMapper, UserLoginRecord> implements UserLoginRecordService {
    @Resource
    private UserLoginRecordMapper userLoginRecordMapper;

    @Override
    public List<UserLoginRecord> listTop50(Long id) {
        List<UserLoginRecord> userLoginRecordList = userLoginRecordMapper.listTop50(id);
        return userLoginRecordList;
    }
}
