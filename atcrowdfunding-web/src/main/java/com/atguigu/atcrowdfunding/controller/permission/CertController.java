package com.atguigu.atcrowdfunding.controller.permission;

import com.atguigu.atcrowdfunding.api.CertService;
import com.atguigu.atcrowdfunding.bean.TCert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CertController {
    @Autowired
    CertService certService;


    //查询全部的cert信息 带分页查询
    @GetMapping("/cert/get")
    public  PageInfo<TCert>  getCertList(@RequestParam(value = "pn",defaultValue = "1") Integer pn,
           @RequestParam(value = "condition",defaultValue="")String condition){

        //分页
        PageHelper.startPage(pn,5);
        List<TCert> list = certService.getAkkCert(condition);

        PageInfo<TCert> pageInfo = new PageInfo<>(list, 5);
        return pageInfo;

    }




    //新增证书--- 因为使用了ajax所以这里要使用GetMapper，  增加返回一个ok就行
    @GetMapping("/cert/add")
    public String addRole(TCert cert){
        certService.saveCert(cert);
        return "ok";
    }


    //删除单本证书
    @GetMapping("/cert/del")
    public String delRole(Integer id){
        certService.delCert(id);
        return "ok";
    }



    //批量删除
    @GetMapping("/cert/delBatch")
    public String delBatchRole(String ids){
        certService.delBatchRole(ids);
        return "OK";
    }




    //查询单个证书    ----- 因为要回显
    @GetMapping("/cset/getSingleCert")
    public TCert getCert(Integer id){
        return certService.getSingleCert(id);
    }



    //更改证书
    @GetMapping("/cert/update")
    public String updateCert(TCert cert){
        certService.updateCert(cert);
        return "ok";
    }


}
