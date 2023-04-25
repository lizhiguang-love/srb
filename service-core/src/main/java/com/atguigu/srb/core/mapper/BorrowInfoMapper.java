package com.atguigu.srb.core.mapper;

import com.atguigu.srb.core.pojo.entity.BorrowInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * <p>
 * 借款信息表 Mapper 接口
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
public interface BorrowInfoMapper extends BaseMapper<BorrowInfo> {
    IPage<BorrowInfo> selectBorrowInfoList(IPage<BorrowInfo> page);
}
