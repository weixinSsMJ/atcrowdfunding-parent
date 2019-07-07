package com.atguigu.atcrowdfunding.controller.permission;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.atguigu.atcrowdfunding.api.RoleService;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.constant.AppConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/*@ResponseBody
@Controller*/

@RestController
public class RoleAjaxCrudController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	RoleService roleService;
	
	//修改
	@PostMapping("/role/update")
	public String updateRole(TRole role) {
		roleService.updateRole(role);
		return "ok";
	}
	
	
	
	//查询role
	@GetMapping("/role/get")
	public TRole getRole(@RequestParam("id") Integer id) {
		TRole role = roleService.getRoleById(id);
		return role;
	}
	
	
	
	
	
	//删除
	@GetMapping("/role/delete")
	public String deleteRole(@RequestParam("ids")String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String [] split = ids.split(",");
			
			for(String string : split) {
				try {
					int id = Integer.parseInt(string);
					roleService.deleteRole(id);
				} catch (NumberFormatException e) {
				}
			}
			return "ok";
		}
		return "fail";
	}
	
	
	
	
	
	@PostMapping("/role/add")
	public String addRole(TRole role) {
		roleService.addRole(role);
		//告诉浏览器此次操作成功还是失败
		return "ok";
	}

	
	
	/**
	 * SpringMVC自动将返回的对象以json字符串的方式交给浏览器
	 * 1、导入jackson的包
	 * 			<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-core</artifactId>
		</dependency>
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
		</dependency>
		2、开启SpringMVC的高级模式
			<mvc:annotation-driven></mvc:annotation-driven>
	 * @return
	 */
	
	@GetMapping("/role/list")
	public PageInfo<TRole> roleList(
					@RequestParam(value = "pn",defaultValue = "1")Integer pn,
					@RequestParam(value = "ps",defaultValue = AppConstant.DEFAULT_PAGE_SIZE)Integer ps,
					@RequestParam(value = "condition",defaultValue = "")String condition) {
		
		
		logger.debug("检索页码：{}--每页大小：{}--查询条件：{}",pn,ps,condition);
		PageHelper.startPage(pn, ps);
		//List<TRole> roles = roleService.getAllRoles();
		List<TRole> roles = roleService.getAllRolesByCondition(condition);
		
		
		PageInfo<TRole> pageInfo = new PageInfo<TRole>(roles, 5);
		return pageInfo;
	}

}
