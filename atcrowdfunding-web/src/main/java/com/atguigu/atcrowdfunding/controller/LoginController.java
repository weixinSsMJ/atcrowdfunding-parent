package com.atguigu.atcrowdfunding.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.atguigu.atcrowdfunding.api.AdminService;
import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.constant.AppConstant;

@Controller
public class LoginController {
	Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	AdminService adminService;
	
	
	/**
	 * controller只用来处理请求（@xxxMapping），调用service进行真正的业务处理(xxxservice.xxxmethod())，
	 * controller判断业务成功还是失败(if)，
	 * 	成功去哪里(return "url")，提示什么东西(域.setAttribute)，
	 * 	失败去哪里，提示什么东西；
	 * 
	 * @param username
	 * @param password
	 * @param session
	 * @param model
	 * @return
	 */
	//@PostMapping("/doLogin")
//	public String login(String username,String password,
//			HttpSession session,
//			Model model) {
//		
//		logger.info("{}用户正在使用{}密码进行登录操作.",username,password);
//		
//		//admin为null说明账号密码错误登录失败，否则就是成功
//		TAdmin admin = adminService.login(username,password);
//		
//		
//		
//		if(admin!=null) {
//			logger.info("{}用户登录成功,,,,",username);
//			//将登录成功的用户保存在session中，
//			session.setAttribute(AppConstant.LOGIN_USER_SESSION_KEY, admin);
//		}else {
//			//登录不成功.来到登录页提示账号密码错误..
//			model.addAttribute(AppConstant.PAGE_MSG_KEY, "账号密码错误，请重试");
//			
//			//只要forward:（转发） redirect:（重定向）视图解析器就不用拼串了。
//			//都加上/；代表当前项目路径
//			
//			//回显账号。
//			model.addAttribute("username", username);
//			return "forward:/login.jsp";
//			
//		}
//		
//		//重定向完全解决重复提交；重定向不能访问受保护的资源
//		return "redirect:/main.html";
//	}
	
	@GetMapping("/main.html")
	public String mainPage(HttpSession session,Model model) {
		//String buy = "";//狗，购，
		//判断登录了没？登录才能访问
		//编程好的习惯；系统里面经常使用的值，而且固定的（特别是k&v的k第一次存就固定，以后都要用）抽取为常量；
		TAdmin loginUser = (TAdmin) session.getAttribute(AppConstant.LOGIN_USER_SESSION_KEY);
		if(loginUser == null) {
			//session中没有就是没登录
			model.addAttribute(AppConstant.PAGE_MSG_KEY, "你还未登录，请先登录");
			return "forward:/login.jsp";
		}else {
			
			//登录才能访问后台主页
			//动态的去数据库查出菜单，并且组装好
			//List<TMenu> meunus =  adminService.listMenus();
			List<TMenu> meunus = adminService.listYourMenus(loginUser.getId());
			
			//放在session。各个页面都要使用菜单
			session.setAttribute(AppConstant.MENU_SESSION_KEY, meunus);
			
			return "main";
		}
		
		
	}
	
	
//	@GetMapping
//	public String toXXpage() {
//		return "";
//	}
	
	@GetMapping("/login")
	public String loginPage() {
		return "forward:/login.jsp";
	}

}
