package com.atguigu.atcrowdfunding.controller.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.atcrowdfunding.api.PermissionService;
import com.atguigu.atcrowdfunding.bean.TPermission;

@RestController
public class PermissonAjaxCrudController {

	
	@Autowired
	PermissionService permissionService;
	
	
	
	@GetMapping("/permission/role/get")
	public List<TPermission> getRolePermissions(@RequestParam("rid")Integer rid){
		
		List<TPermission> permissions =  permissionService.getRolePermissions(rid);
		return permissions;
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 给角色分配权限
	 * @param rid
	 * @param permissionIds
	 * @return
	 */
	@PostMapping("/permission/role/assign")
	public String roleAssignPermission(@RequestParam("rid")Integer rid,
			@RequestParam("permissionIds")String permissionIds) {
		
		permissionService.assignPermissionForRole(rid,permissionIds);
		return "ok";
	}
	
	
	
	
	
	
	
	
	/*
	 * 查询所有权限
	 */
	@GetMapping("/permission/list")
	public List<TPermission> getAllPermissions(){
		return permissionService.getAllPermissions();
	}
	
	
	
	/**
	 * 保存权限
	 * @param permission
	 * @return
	 */
	@PostMapping("/permission/add")
	public String savePermission(TPermission permission) {
		permissionService.savePermission(permission);
		return "ok";
	}
	
	
	
	/**
	 * 更新权限
	 * @param permission
	 * @return
	 */
	@PostMapping("/permission/update")
	public String updatePermission(TPermission permission) {
		permissionService.updatePermission(permission);
		return "ok";
	}
	
	
	
	
	/**
	 * 删除权限
	 * @param id
	 * @return
	 */
	
	@GetMapping("/permission/delete")
	public String deletePermission(Integer id) {
		permissionService.deletePermission(id);
		return "ok";
	}
	
	
	
	
	/**
	 * 获取权限
	 * @param id
	 * @return
	 */
	
	@GetMapping("/permission/get")
	public TPermission getPermission(Integer id) {
		TPermission permission = permissionService.getPermissionById(id);
		return permission;
	}
	
	
	
	
	
}
