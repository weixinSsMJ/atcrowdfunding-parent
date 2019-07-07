package com.atguigu.atcrowdfunding.api;

import com.atguigu.atcrowdfunding.bean.TType;

import java.util.List;

public interface ProjectTypeService {

    //查询全部
    List<TType> getAllType(String condition);


    //新增
    void saveType(TType type);


    //删除单本证书
    void delType(Integer id);



    //批量删除
    void delBatchType(String ids);


    //查询单本证书来回显
    TType getSingleType(Integer id);


    //更新图书
    void  updateType(TType type);






}
