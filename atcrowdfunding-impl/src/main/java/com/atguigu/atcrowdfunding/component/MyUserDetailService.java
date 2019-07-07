package com.atguigu.atcrowdfunding.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TPermission;
import com.atguigu.atcrowdfunding.bean.TRole;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TPermissionMapper;
import com.atguigu.atcrowdfunding.mapper.TRoleMapper;

@Component("myUserDetailService")
public class MyUserDetailService implements UserDetailsService {
	
	@Autowired
	TAdminMapper adminMapper;
	@Autowired
	TRoleMapper roleMapper;
	@Autowired
	TPermissionMapper permissionMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(username);
		List<TAdmin> list = adminMapper.selectByExample(example);
		if(list == null || list.size()==0) {
			//没查到
			return null;
		}
		if(list.size() != 1) {
			return null;
		}else {
			TAdmin tAdmin = list.get(0);
			List<GrantedAuthority> grantedAuthoritys = new ArrayList<GrantedAuthority>();
			//1、查出当前用户的角色信息
			List<TRole> roles = roleMapper.getUserHasRole(tAdmin.getId());
			for (TRole role : roles) {
				grantedAuthoritys.add(new SimpleGrantedAuthority("ROLE_"+role.getName()));
				//2、查出当前角色的所有权限放入集合
				List<TPermission> permissions = permissionMapper.selectRolePermissions(role.getId());
				for (TPermission permiss : permissions) {
					String name = permiss.getName();
					if(!StringUtils.isEmpty(name)) {
						grantedAuthoritys.add(new SimpleGrantedAuthority(name));
					}
				}
			}
			//
			
			/**
			 * String username, 
			 * String password, 
			 * boolean enabled,
			 * boolean accountNonExpired, 
			 * boolean credentialsNonExpired,
			 * boolean accountNonLocked, 
			 * Collection<? extends GrantedAuthority> authorities
			 */
			MyUserDetail user = new MyUserDetail(
					tAdmin.getLoginacct(), 
					tAdmin.getUserpswd(), 
					grantedAuthoritys, 
					tAdmin);
			
			return user;
		}
	}

}
