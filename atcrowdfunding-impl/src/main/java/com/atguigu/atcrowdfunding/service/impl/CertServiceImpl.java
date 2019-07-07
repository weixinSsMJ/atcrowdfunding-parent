package com.atguigu.atcrowdfunding.service.impl;

import com.atguigu.atcrowdfunding.api.CertService;
import com.atguigu.atcrowdfunding.bean.TCert;
import com.atguigu.atcrowdfunding.bean.TCertExample;
import com.atguigu.atcrowdfunding.mapper.TCertMapper;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CertServiceImpl implements CertService {
    @Autowired
    TCertMapper certMapper;


    //资质维护 查询全部的cert信息
    public List<TCert> getAkkCert(String condition){
        TCertExample example = new TCertExample();
        example.createCriteria().andNameLike("%"+condition+"%");

       return certMapper.selectByExample(example);
    }

    //新增证书
    public void saveCert(TCert cert){

        certMapper.insertSelective(cert);
    }


    //删除单本证书
    public void delCert(Integer id){

        certMapper.deleteByPrimaryKey(id);
    }



    //批量删除
    public void delBatchRole(String ids){
        //批量删除要先判断是否为空
        if (!StringUtil.isEmpty(ids)){
            String[] split = ids.split(",");//用 ,  来间隔

            for (String string :split){ //把要删除的批量遍历出来先

                int i = Integer.parseInt(string);//转换成整型，等于key，再调用删除id 的方法
                certMapper.deleteByPrimaryKey(i);
            }
        }
    }




    //查询单个证书    ----- 因为要回显所以返回值要TCert
    public TCert getSingleCert(Integer id){

        return certMapper.selectByPrimaryKey(id);
    }




    //更改证书
    @Override
    public void updateCert(TCert cert) {
        certMapper.updateByPrimaryKeySelective(cert);//为什么用这个方法？？   主键约束，有选择性的修改更新

    }

}
