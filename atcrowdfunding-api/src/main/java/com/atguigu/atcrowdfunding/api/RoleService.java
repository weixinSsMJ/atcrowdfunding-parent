package com.atguigu.atcrowdfunding.api;

import java.util.List;

import com.atguigu.atcrowdfunding.bean.TRole;

public interface RoleService {

	/**
	 *  返回所有的role数据
	 * @return
	 */
	List<TRole> getAllRoles();

	/**
	 * 按照检索条件查询
	 * @param condition
	 * @return
	 */
	List<TRole> getAllRolesByCondition(String condition);

	
	
	//角色添加
	void addRole(TRole role);
	//删除
	void deleteRole(int id);

	
	
	//查询role
	TRole getRoleById(Integer id);
	
	//修改角色
	void updateRole(TRole role);

	
	//查出已有的角色
	List<TRole> getUserHasRoles(Integer id);
	//查出没有的角色
	List<TRole> getUserUnRoles(Integer id);

	
	
	/*
	 * 给用户分配指定的角色
	 */
	void assignUserRole(Integer uid, String rids);
	
	void unAssignUserRole(Integer uid, String rids);

}
