package com.atguigu.srb.core.service.impl;

import com.atguigu.srb.core.pojo.entity.BorrowerAttach;
import com.atguigu.srb.core.mapper.BorrowerAttachMapper;
import com.atguigu.srb.core.pojo.vo.BorrowerAttachVO;
import com.atguigu.srb.core.service.BorrowerAttachService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 借款人上传资源表 服务实现类
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Service
public class BorrowerAttachServiceImpl extends ServiceImpl<BorrowerAttachMapper, BorrowerAttach> implements BorrowerAttachService {

    @Override
    public List<BorrowerAttachVO> selectBorrowerAttachVOList(Long id) {
        QueryWrapper<BorrowerAttach> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("borrower_id",id);
        List<BorrowerAttachVO> borrowerAttachVOList = new ArrayList<>();
        List<BorrowerAttach> borrowerAttachList = baseMapper.selectList(queryWrapper);
        borrowerAttachList.forEach(borrowerAttach -> {
            BorrowerAttachVO borrowerAttachVO = new BorrowerAttachVO();
            borrowerAttachVO.setImageType(borrowerAttach.getImageType());
            borrowerAttachVO.setImageUrl(borrowerAttach.getImageUrl());
            borrowerAttachVOList.add(borrowerAttachVO);
        });
        return borrowerAttachVOList;
    }
}
