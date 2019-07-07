package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.api.TagService;
import com.atguigu.atcrowdfunding.bean.TTag;
import com.atguigu.atcrowdfunding.mapper.TTagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceimpl implements TagService {

    @Autowired
    TTagMapper tTagMapper;


    //查询全部
    @Override
    public List<TTag> getAllTag() {
        return tTagMapper.selectByExample(null);
    }

    //新增
    @Override
    public void saveTag(TTag tTag) {
        tTagMapper.insertSelective(tTag);
    }

    //删除
    @Override
    public void delTag(Integer id) {
        tTagMapper.deleteByPrimaryKey(id);
    }

    //更新修改
    @Override
    public void updateTag(TTag tag) {
        tTagMapper.updateByPrimaryKeySelective(tag);
    }

    //查询单个回显
    @Override
    public TTag getTag(Integer id) {
        return tTagMapper.selectByPrimaryKey(id);
    }


}
