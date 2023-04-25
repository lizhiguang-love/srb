package com.atguigu.srb.core.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.srb.core.listener.ExcelDictDTOListener;
import com.atguigu.srb.core.pojo.dto.ExcelDictDTO;
import com.atguigu.srb.core.pojo.entity.Dict;
import com.atguigu.srb.core.mapper.DictMapper;
import com.atguigu.srb.core.service.DictService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 数据字典 服务实现类
 * </p>
 *
 * @author lizhiguang
 * @since 2022-06-18
 */
@Service
@Slf4j
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {
    @Resource
    private RedisTemplate redisTemplate;
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void importData(InputStream inputStream) {
        EasyExcel.read(inputStream, ExcelDictDTO.class,new ExcelDictDTOListener(baseMapper)).sheet().doRead();
    }

    @Override
    public List<ExcelDictDTO> listDictData() {
        List<Dict> dictList = baseMapper.selectList(null);

        List<ExcelDictDTO> excelDictDTOList = new ArrayList<>(dictList.size());
        for (Dict dict : dictList) {
            ExcelDictDTO excelDictDTO = new ExcelDictDTO();
            BeanUtils.copyProperties(dict,excelDictDTO);
            excelDictDTOList.add(excelDictDTO);
        }
        return excelDictDTOList;
    }

    @Override
    public List<Dict> listByParentId(Long parentId) {
        //从redis中取数据
        try {
            //使用try/catch是防止连接redis异常，导致无法查询数据
            List<Dict> dictLists = (List<Dict>)redisTemplate.opsForValue().get("srb:core:DictList" + parentId);
            if (dictLists != null){
                log.info("从redis中取数据");
                return dictLists;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",parentId);
        List<Dict> dictList = baseMapper.selectList(queryWrapper);
        if (!dictList.isEmpty()){
            for (Dict dict : dictList) {
                boolean hasChildren = this.isHasChildren(dict.getId());
                dict.setHasChildren(hasChildren);
            }
            try {
                //使用try/catch是防止连接redis异常，导致展示数据
                redisTemplate.opsForValue().set("srb:core:DictList" + parentId,dictList,5, TimeUnit.MINUTES);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return dictList;
        }
        return null;
    }

    @Override
    public List<Dict> findByDictCode(String dictCode) {
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictCode);
        Dict dict = baseMapper.selectOne(wrapper);
        return this.listByParentId(dict.getId());
    }

    @Override
    public String getNameByParentDictCodeAndValue(String dictCode, Integer value) {
        QueryWrapper<Dict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_code",dictCode);
        Dict dictParent = baseMapper.selectOne(queryWrapper);
        if (dictParent==null){
            return "";
        }
        queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("parent_id",dictParent.getId())
                .eq("value",value);
        Dict dict = baseMapper.selectOne(queryWrapper);
        if (dict==null){
            return null;
        }
        return dict.getName();
    }

    private boolean isHasChildren(Long id){
        QueryWrapper queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count !=null && count != 0){
            return true;
        }
        return false;
    }
}
