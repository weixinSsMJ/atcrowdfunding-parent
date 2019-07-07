package com.atguigu.atcrowdfunding.controller.permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.atcrowdfunding.api.MenuService;
import com.atguigu.atcrowdfunding.api.PermissionService;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;

@RestController
public class MenuAjaxCrudController {

	@Autowired
	MenuService menuService;
	
	@Autowired
	PermissionService permissionService;
	
	
	  
	/**
	 * 获取当前权限对应的所有菜单
	 * @return
	 */
	@GetMapping("/menu/permisson/get")
	public List<TMenu> getPermissionMenus(@RequestParam("pid")Integer permissionId){
		List<TMenu> menus = permissionService.getMenusByPermissionId(permissionId);
		return menus;
	}
	
	
	
	
	
	
	
	/*
	 * 为指定的权限，分配它可以操作的菜单
	 * permissionId  权限id
	 * menuIds   菜单的id集合，用,分割
	 * 
	 */
	@PostMapping("/menu/permisson/save")
	public String saveMenuPermissions(Integer perimissionId,String menuIds) {
		menuService.saveMenuPermissions(perimissionId,menuIds);
		return "ok";
	}
	
	
	
	
	
	/*
	 * 查询所有菜单
	 */
	@GetMapping("/menu/list")
	public List<TMenu> getAllMenus(){
		return menuService.getAllMenus();
	}
	
	
	
	//保存
	@PostMapping("/menu/add")
	public String saveMenu(TMenu menu) {
		menuService.saveMenu(menu);
		return "ok";
	}
	
	@PutMapping("/menu/update")
	public String updateMenu(TMenu menu) {
		menuService.updateMenu(menu);
		return "ok";
	}
	
	
	@GetMapping("/menu/delete")
	public String deleteMenu(Integer id) {
		menuService.deleteMenu(id);
		return "ok";
	}
	
	
	
	
	
	@GetMapping("/menu/get")
	public TMenu getMenu(Integer id) {
		TMenu menu = menuService.getMenuById(id);
		return menu;
	}
	
}
