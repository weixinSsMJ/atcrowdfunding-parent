package com.atguigu.atcrowdfunding.controller.permission;

import java.awt.Menu;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.atcrowdfunding.api.AdminService;
import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.constant.AppConstant;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Controller
public class PermissionPageController {
	
	
	@Autowired
	AdminService adminService;
	
	/*
	 * 跳到菜单列表页
	 */
	
	@GetMapping("/menu/index.html")
	public String MenuPage() {
		return "permission/menu";
	}
	
	
	
	
	
	
	
	/**
	 * 写所有去页面的请求映射
	 * @return
	 */
	@GetMapping("/admin/index.html")
	public String userPage(Model model,
			@RequestParam(value="pn",defaultValue = "1")Integer pn,
			@RequestParam(value="ps",defaultValue = AppConstant.DEFAULT_PAGE_SIZE)Integer ps,
			@RequestParam(value = "condition",defaultValue = "")String condition,
			HttpSession session) {
		
		//将查询条件放在session中，
		session.setAttribute(AppConstant.QUERY_CONDITION_KEY, condition);
		session.setAttribute("pn", pn);
		
		//查出系统中所有的用户TAdmin。
		PageHelper.startPage(pn, ps);
		//List<TAdmin> admins =  adminService.listAllAdmin();
		List<TAdmin> admins = adminService.listAllAdminByCondition(condition);
		
		PageInfo<TAdmin> page = new PageInfo<TAdmin>(admins, AppConstant.CONTINUES_PAGE_NUM);
		
		model.addAttribute("page", page);
		return "permission/user";
	}
	
	
	
	
	
	
	
	/**
	 * 调到role页面的
	 * @throws InterruptedException 
	 */
	@GetMapping("/role/index.html")
	public String rolePage() throws InterruptedException {
		//1、查出所有的role，放到域中，转发到role页面进行遍历显示
		//Thread.sleep(5000);
		return "permission/role";
	}
	
	//
	@GetMapping("/permission/index.html")
	public String permissionPage() {
		
		return "permission/permission";
	}
}
