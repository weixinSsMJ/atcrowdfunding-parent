package com.atguigu.atcrowdfunding.controller.permission;

import com.atguigu.atcrowdfunding.api.TagService;
import com.atguigu.atcrowdfunding.bean.TTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TagController {
    @Autowired
    TagService tagService;


    //查询全部项目标签
    @GetMapping("/tag/list")
    public List<TTag> getAllTag(){
        return tagService.getAllTag();
    }



    //新增
    @GetMapping("/tag/save")
    public String saveTag(TTag tTag){
        tagService.saveTag(tTag);
        return "ok";
    }



    //删除
    @GetMapping("/tag/delete")
    public String delTag(Integer id){
        tagService.delTag(id);
        return "ok";
    }



    //更新
    @GetMapping("/tag/update")
    public String updateTag(TTag tTag){
        tagService.updateTag(tTag);
        return "ok";
    }



    //单本查询回显
    @GetMapping("/tag/get")
    public TTag getTag(Integer id){
        return tagService.getTag(id);

    }

}
