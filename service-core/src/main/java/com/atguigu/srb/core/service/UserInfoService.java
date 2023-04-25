package com.atguigu.srb.core.service;

import com.atguigu.srb.core.pojo.entity.UserInfo;
import com.atguigu.srb.core.pojo.query.UserInfoQuery;
import com.atguigu.srb.core.pojo.vo.LoginVO;
import com.atguigu.srb.core.pojo.vo.RegisterVO;
import com.atguigu.srb.core.pojo.vo.UserInfoVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户基本信息 服务类
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
public interface UserInfoService extends IService<UserInfo> {

    void register(RegisterVO registerVO);

    UserInfoVO login(LoginVO loginVO,String ip);

    /**
     *
     * @param userInfoQuery 查询条件对象
     * @param page 每页显示多少条
     * @return
     */
    IPage<UserInfo> listPage(long page,long limit, UserInfoQuery userInfoQuery);
    void lock(long id,Integer status);

    boolean checkMobile(String mobile);

    List<UserInfo> selectUser(int pageSize,int pageNum);


}
