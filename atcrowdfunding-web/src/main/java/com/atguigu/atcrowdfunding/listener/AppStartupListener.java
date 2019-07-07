package com.atguigu.atcrowdfunding.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 1、编写监听器
 * 2、配置在web.xml中
 * @author lfy
 *
 */
public class AppStartupListener  implements ServletContextListener{
	Logger logger = LoggerFactory.getLogger(AppStartupListener.class);
	/**
	 * 项目初始化；给application域中保存项目路径
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		
		// TODO Auto-generated method stub
		ServletContext servletContext = sce.getServletContext();
		
		//当前项目路径，以/开始，不/结束
		String contextPath = servletContext.getContextPath();
		
		servletContext.setAttribute("ctx", contextPath);
		logger.info("项目启动成功...访问路径是{}",contextPath);
		
		
		
	}

	/**
	 * 项目销毁
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		
	}

}
