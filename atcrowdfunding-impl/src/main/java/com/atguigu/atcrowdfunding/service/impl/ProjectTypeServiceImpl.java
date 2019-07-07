package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.api.ProjectTypeService;
import com.atguigu.atcrowdfunding.bean.TType;
import com.atguigu.atcrowdfunding.bean.TTypeExample;
import com.atguigu.atcrowdfunding.bean.TTypeExample.Criteria;
import com.atguigu.atcrowdfunding.mapper.TTypeMapper;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectTypeServiceImpl  implements ProjectTypeService {

    @Autowired
    TTypeMapper tTypeMapper;

    //查询全部的type信息
    @Override
    public List<TType> getAllType(String condition) {

        TTypeExample example = new TTypeExample();
        example.createCriteria().andNameLike("%"+condition+"%");
        Criteria c =  example.createCriteria();//两个模糊查询
        c.andRemarkLike("%"+condition+"%");
        example.or(c);

        return tTypeMapper.selectByExample(example);
    }



    //新增证书
    public void saveType(TType type){
        tTypeMapper.insertSelective(type);
    }





    //删除单本证书
    @Override
    public void delType(Integer id) {
        tTypeMapper.deleteByPrimaryKey(id);
    }





    //批量删除
    public void delBatchType(String ids){
        if (!StringUtil.isEmpty(ids)){
            String[] split = ids.split(",");

            for (String string:split){
                int i = Integer.parseInt(string);

                tTypeMapper.deleteByPrimaryKey(i);

            }

        }

    }



    //查询单本证书来回显
   public TType getSingleType(Integer id){

        return tTypeMapper.selectByPrimaryKey(id);
   }



    //更新图书
    public void updateType(TType type){
        tTypeMapper.updateByPrimaryKeySelective(type);
    }



}
