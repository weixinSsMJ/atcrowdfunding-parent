package com.atguigu.atcrowdfunding.api;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.exception.EmailExistException;
import com.atguigu.atcrowdfunding.exception.LoginacctExistException;

public interface AdminService {

	TAdmin login(String username, String password);

	/**
	 * 查出所有菜单组装好父子关系
	 * @return
	 */
	List<TMenu> listMenus();

	/**
	 * 查出系统所有的用户
	 * @return
	 */
	List<TAdmin> listAllAdmin();

	/**
	 * 带条件查询所有Admin
	 * @param condition
	 * @return
	 */
	List<TAdmin> listAllAdminByCondition(String condition);

	/**
	 * 按照id查询用户
	 * @param id
	 * @return
	 */
	TAdmin getAdminById(Integer id);

	/**
	 * 按照id修改admin的值
	 * @param admin
	 */
	void updateAdmin(TAdmin admin);

	/**
	 * 保存admin对象
	 * @param admin
	 */
	void saveAdmin(TAdmin admin) throws LoginacctExistException,EmailExistException;
	
	
	//检查账号是否可用   true：代表可用  false：不可用（已经被占用）
	boolean checkLoginacct(TAdmin admin);

	//检查邮箱是否可用  true：代表可用  false：不可用（已经被占用）
	public boolean checkEmail(TAdmin admin);

	/**
	 * 按照id删除
	 * @param id
	 */
	void deleteAdminById(Integer id);

	/*
	 * 查询用户自己能操作的菜单
	 */
	List<TMenu> listYourMenus(Integer id);

}
