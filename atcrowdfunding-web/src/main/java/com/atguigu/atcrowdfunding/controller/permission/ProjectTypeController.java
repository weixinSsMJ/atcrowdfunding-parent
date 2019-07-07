package com.atguigu.atcrowdfunding.controller.permission;

import com.atguigu.atcrowdfunding.api.ProjectTypeService;
import com.atguigu.atcrowdfunding.bean.TType;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ProjectTypeController {

    @Autowired
    ProjectTypeService projectTypeService;


    @GetMapping("/projectType/get")
    public PageInfo<TType> getTypeList(@RequestParam(value="pn",defaultValue="1")Integer pn,
                 @RequestParam(value="condition",defaultValue="")String condition) {
        PageHelper.startPage(pn,5);
        List<TType> list = projectTypeService.getAllType(condition);

        PageInfo<TType> info = new PageInfo<>(list, 5);
        return info;
    }




    //新增证书
    @GetMapping("/projectType/add")
    public String addRole(TType cert){
        projectTypeService.saveType(cert);
        return "ok";
    }



    //删除单本
    @GetMapping("/projectType/del")
    public String delRole(Integer id){
        projectTypeService.delType(id);
        return"ok";
    }


    //批量删除
    @GetMapping("/projectType/delBatch")
    public String delBatchType(String ids){
        projectTypeService.delBatchType(ids);
        return "ok";
    }



    //查询单本来回显
    @GetMapping("/projectType/getSingleType")
    public TType getType(Integer id){
        return projectTypeService.getSingleType(id);
    }



    //更新
    @GetMapping("/projectType/update")
    public String updateType(TType type){
        projectTypeService.updateType(type);
        return "ok";
    }




}
