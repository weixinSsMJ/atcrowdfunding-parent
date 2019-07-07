package com.atguigu.atcrowdfunding.controller.permission;

import java.util.List;

import javax.management.relation.Role;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.atcrowdfunding.api.AdminService;
import com.atguigu.atcrowdfunding.api.RoleService;
import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.constant.AppConstant;
import com.atguigu.atcrowdfunding.exception.EmailExistException;
import com.atguigu.atcrowdfunding.exception.LoginacctExistException;

/**
 * 和admin有关的所有操作
 * @author lfy
 *
 */
@Controller
public class AdminCrudController {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	AdminService adminService;
	
	
	@Autowired
	RoleService roleService;
	
	
	/*
	 * 给用户分配指定的角色
	 * uid 用户id
	 * rids 所有角色id  用 , 分隔
	 */
	@GetMapping("/user/unassign/role")
	public String unAssignUserRole(@RequestParam("uid")Integer uid,
			@RequestParam("rids")String  rids) {
				
		roleService.unAssignUserRole(uid,rids);
		
		return "redirect:/user/assignRole.html?id="+uid;
	}
	
	
	
	
	
	
	
	
	/*
	 * 给用户分配指定的角色
	 * uid 用户id
	 * rids 所有角色id  用 , 分隔
	 */
	@GetMapping("/user/assign/role")
	public String assignUserRole(@RequestParam("uid")Integer uid,
			@RequestParam("rids")String  rids) {
				
		roleService.assignUserRole(uid,rids);
		
		return "redirect:/user/assignRole.html?id="+uid;
	}
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * 来到用户的角色分配页面
	 * id 用户id
	 */
	@GetMapping("/user/assignRole.html")
	public String toAssignRolePage(@RequestParam("id")Integer id,Model model) {
		
		//查出用户已有的
		List<TRole> roles = roleService.getUserHasRoles(id);
		//查出用户没有的角色
		List<TRole> unRoles = roleService.getUserUnRoles(id);
		
		
		model.addAttribute("roles",roles);//已有的角色
		model.addAttribute("unRoles",unRoles);//没有的角色
		
		return "permission/user-role";
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 传递所有的选中的ids
	 * @param ids： 1,a,3,4,5
	 * @param session
	 * @param model
	 * @return
	 */
	@GetMapping("/user/batch/delete")
	public String deleteBatchAdmin(@RequestParam("ids")String ids,
			HttpSession session,
			Model model) {
		String  condition = (String) session.getAttribute(AppConstant.QUERY_CONDITION_KEY);
		Integer pn = (Integer) session.getAttribute("pn");
		model.addAttribute("condition", condition);
		model.addAttribute("pn", pn);
		
		
		//1、分割id，并批量删除
		if(!StringUtils.isEmpty(ids)) {
			//ids=null
			String[] split = ids.split(",");
			for (String id:split) {
				try {
					int i = Integer.parseInt(id);
					adminService.deleteAdminById(i);
				} catch (NumberFormatException e) {
				}
			}
			
		}
		
		
		return "redirect:/admin/index.html";
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	@GetMapping("/user/delete")
	public String deleteAdmin(@RequestParam("id")Integer id,
			HttpSession session,
			Model model) {
		String  condition = (String) session.getAttribute(AppConstant.QUERY_CONDITION_KEY);
		Integer pn = (Integer) session.getAttribute("pn");
		model.addAttribute("condition", condition);
		model.addAttribute("pn", pn);
		
		//删除
		adminService.deleteAdminById(id);
		return "redirect:/admin/index.html";
	}
	
	//如果某个请求是以html结尾那就是跳转页面，否则就是进行真正的操作
	/**
	 *	 如果封装请求参数的是一个对象TAdmin=== TAdmin；HelloWorld==helloWorld;
	 * 	springmvc会自动的将这个对象放在model中，
	 * 			key用的是TAdmin；驼峰规则；如果首字母和紧挨的字母都是大写，驼峰出来也是大写
	 * 			key的默认规则是按照类名首字母小写的驼峰方式做的；
	 * 			
	 * 		
	 * @param admin
	 * @param model
	 * @return
	 */
	@PostMapping("/user/add")
	public String addAdmin(TAdmin admin,Model model) {
		logger.debug("将要添加的用户是：{}",admin);
		boolean vaild = true;
		
		try {
			//1、异常机制只能获取到一个异常
			//1、可以使用异常机制，使得上层感知下层的状态
			adminService.saveAdmin(admin);
		} catch (LoginacctExistException e) {
			//1、账号占用
			vaild = false;
			model.addAttribute("loginacctmsg", e.getMessage());
			logger.error("{}用户添加出错：{}；",admin,e);
			
		} catch (EmailExistException e) {
			//1、邮箱占用
			vaild = false;
			model.addAttribute("emailmsg", e.getMessage());
			logger.error("{}用户添加出错：{}；",admin,e);
		}
		
		if(vaild==false) {
			//1、校验失败
			return "permission/user-add";
		}
		
		
		//新增完成以后跳转到最后一页，利用分页合理化机制，跳到最后一页
		return "redirect:/admin/index.html?pn="+Integer.MAX_VALUE;
	}
	
	/**
	 * 来到用户的添加页面
	 */
	@GetMapping("/user/add.html")
	public String addPage() {
		
		//来到用户添加页
		return "permission/user-add";
	}
	
	
	
	/**
	 * 来到用户修改页面
	 * @return
	 */
	@GetMapping("/user/edit.html")
	public String toEditPage(@RequestParam("id")Integer id,
			Model model) {
		
		//查出当前用户信息
		TAdmin admin = adminService.getAdminById(id);
		
		model.addAttribute("data", admin);
		return "permission/user-edit";
	}
	
	/**
	 * admin修改请求
	 * @return
	 */
	@PostMapping("/user/update")
	public String updateAdmin(TAdmin admin,HttpSession session,Model model) {
		
		//admin收集了用户的id，和其他要修改的信息
		logger.info("用户{}信息正在修改...{}",admin.getLoginacct(),admin);
		adminService.updateAdmin(admin);
		Integer pn = (Integer) session.getAttribute("pn");
		String condition = (String) session.getAttribute(AppConstant.QUERY_CONDITION_KEY);
		
		//浏览器的url地址中文一定要编码才行；Base64 UTF-8
		
		model.addAttribute("pn", pn);
		model.addAttribute("condition", condition);
		
		
		/**
		 * SpringMVC的特性；
		 * 1）、我们给Model中放的数据
		 * 		1-如果是转发到下一个页面；数据是在请求域中  ${requestScope.param}
		 * 		2-如果是重定向到下一个页面，数据是放在url地址后面以请求参数的方式。而且解决了中文乱码问题
		 */
		//重新来到用户列表页
		return "redirect:/admin/index.html";
	}

}
