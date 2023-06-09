package com.atguigu.srb.core.mapper;

import com.atguigu.srb.core.pojo.entity.UserAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
 * 用户账户 Mapper 接口
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    void updateAccount(
            @Param("bindCode")String bindCode,
            @Param("amount") BigDecimal amount,
            @Param("freezeAmount")BigDecimal freezeAmount);

    UserAccount getAccount(@Param("bindCode")String bindCode);
}
