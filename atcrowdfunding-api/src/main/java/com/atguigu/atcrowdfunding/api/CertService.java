package com.atguigu.atcrowdfunding.api;


import com.atguigu.atcrowdfunding.bean.TCert;

import java.util.List;

public interface CertService {

    //查询
    List<TCert> getAkkCert(String condition);

    //新增
    void saveCert(TCert cert);

    //删除单本证书
    void delCert(Integer id);


    //批量删除
    void delBatchRole(String ids);

    //查询单个证书    ----- 因为要回显
    TCert getSingleCert(Integer id);



    //修改证书
    void updateCert (TCert cert);

}
