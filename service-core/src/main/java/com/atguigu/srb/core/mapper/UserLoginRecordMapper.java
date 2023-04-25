package com.atguigu.srb.core.mapper;

import com.atguigu.srb.core.pojo.entity.UserLoginRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户登录记录表 Mapper 接口
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Mapper
public interface UserLoginRecordMapper extends BaseMapper<UserLoginRecord> {
    List<UserLoginRecord> listTop50(@Param("userId") long id);
}
