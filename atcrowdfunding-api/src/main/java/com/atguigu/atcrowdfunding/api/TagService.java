package com.atguigu.atcrowdfunding.api;

import com.atguigu.atcrowdfunding.bean.TTag;
import com.atguigu.atcrowdfunding.bean.TType;

import java.util.List;

public interface TagService {

    //查询项目标签
    List<TTag> getAllTag();


    //新增
    void saveTag(TTag tTag);


    //删除
    void delTag(Integer id);


    //修改
    void updateTag(TTag tag);


    //查询单个回显
    TTag getTag(Integer id);






















}
