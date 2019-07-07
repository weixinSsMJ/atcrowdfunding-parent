package com.atguigu.atcrowdfunding.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.component.MyUserDetail;
import com.atguigu.atcrowdfunding.constant.AppConstant;

/**
 * 这个组件能不能扫描到？
 * 
 * SpringSecurity
 * 1、导包（web模块放在web层，核心放在业务层，）
 * 2、写web.xml配置
 * 		
 * @author lfy
 *
 */
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class AtcrowdfundingSecurityConfig extends WebSecurityConfigurerAdapter {
	
	public AtcrowdfundingSecurityConfig() {
		System.out.println("AtcrowdfundingSecurityConfig..被创建,,,.,,");
	}
	
	@Qualifier("myUserDetailService")
	@Autowired
	UserDetailsService userDetailsService;
	
	@Qualifier("MyPasswordEncoder")
	@Autowired
	PasswordEncoder passwordEncoder;
	/**
	 * 定义登录的认证规则
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(auth);
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder);
	}
	
	/**
	 * 定义http请求访问的授权规则
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		//super.configure(http);
		http.authorizeRequests()
			.antMatchers("/static/**").permitAll()
			.antMatchers("/index.jsp","/login.jsp","/reg.jsp").permitAll()
			.anyRequest().authenticated();
		
		/**
		 * 	跨站请求伪造；
		 * 		form表单提交的时候都必须带一个_csrf令牌。
		 */
		http.csrf().disable();
		
		//表单登录功能
		/**
		 * GET /login 去登录页
		 * POST /login 去登录请求
		 * 
		 * successHandler：登录成功以后怎么做
		 * failureHandler：登录失败以后怎么做
		 */
		http.formLogin().loginPage("/login")
			.usernameParameter("loginacct")
			.passwordParameter("pwd")
			.successHandler(new AuthenticationSuccessHandler() {
				@Override
				public void onAuthenticationSuccess(
						HttpServletRequest request, 
						HttpServletResponse response,
						Authentication authentication) throws IOException, ServletException {
					//SpringSecurity登录成功以后；
					System.out.println("----------");
					//1、把当前成功用户放在session中？
					//当前登录的用户的对象
					UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
					
					MyUserDetail principal = (MyUserDetail) token.getPrincipal();//封装了当前登录的用户
					TAdmin admin = principal.getAdmin();
					System.out.println("----------"+admin);
					request.getSession().setAttribute(AppConstant.LOGIN_USER_SESSION_KEY, admin);
					//登录成功去main页面
					response.sendRedirect(request.getContextPath()+"/main.html");
				}
				
			})
			.failureHandler(new AuthenticationFailureHandler() {
				@Override
				public void onAuthenticationFailure(
						HttpServletRequest request, 
						HttpServletResponse response,
						AuthenticationException exception) throws IOException, ServletException {
					// TODO Auto-generated method stub
					
					request.setAttribute("msg", "账号密码错误，请重试");
					request.getRequestDispatcher("/login.jsp").forward(request, response);
				}
				
			}).permitAll();
		
		
		
		/*http.addFilterAfter(null, UsernamePasswordAuthenticationFilter.class);*/
		
	}

}
