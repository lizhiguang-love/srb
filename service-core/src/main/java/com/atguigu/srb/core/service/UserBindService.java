package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.UserBind;
import com.atguigu.srb.core.pojo.vo.UserBindVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 用户绑定表 服务类
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
public interface UserBindService extends IService<UserBind> {

    String commitBindUser(UserBindVO userBindVO,Long userId);

    void callback(Map<String,Object> map);

    String getBindCodeByUserId(Long userId);
}
