package com.atguigu.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.atcrowdfunding.api.AdminService;
import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 1、引入Spring的单元测试
 * 		1)、引入junit
 * 		2）、引入Spring-test模块
 * 2、将普通的单元测试变为spring的单元测试
 * 		@RunWith：告诉junit，使用spring框架驱动这个测试；
 * 		spring编写了一个SpringJUnit4ClassRunner驱动类
 * 
 * 3、@ContextConfiguration 告诉Spring，当前单元测试，需要使用的配置文件是哪些;
 * 	
 * @author lfy
 *
 *
 * 如何使用分页；（PageHelper【mybatis的一个第三方插件】）
 * 	1）、导入PageHelper
 * 	2）、PageHelper配置在mybatis的全局配置文件中
 *  3）、在需要分页查询的方法前面PageHelper.startPage(1, 3);即可分页;
 *  	PageHelper紧跟的后面的第一个方法会被分页。
 */
@ContextConfiguration(locations = {"classpath*:spring-beans.xml","classpath*:spring-mybatis.xml","classpath*:spring-tx.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AdminServiceTest {
	Logger logger = LoggerFactory.getLogger(AdminServiceTest.class);
	@Autowired
	AdminService adminService;
	
	@Autowired
	TAdminMapper adminMapper;
	
	@Test
	public void pageTest() {
		
		
		//
		PageHelper.startPage(5, 1);
		List<TAdmin> admin = adminService.listAllAdmin();
		
//		PageHelper.startPage(1, 3);
//		List<TAdmin> admin2 = adminService.listAllAdmin();
//		System.out.println(admin.size());
//		System.out.println(admin2.size());
		
		PageInfo<TAdmin> pageInfo = new PageInfo<TAdmin>(admin,5);
		
		logger.info("当前第{}页",pageInfo.getPageNum());
		logger.info("总共{}页",pageInfo.getPages());
		logger.info("查出的记录{}",pageInfo.getList());
		logger.info("连续分页如下");
		int[] nums = pageInfo.getNavigatepageNums();
		for (int i : nums) {
			System.out.println(i);
		}
		
		
	}

}
