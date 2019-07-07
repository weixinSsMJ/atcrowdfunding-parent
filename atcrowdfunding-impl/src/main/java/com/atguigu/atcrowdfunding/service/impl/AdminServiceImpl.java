package com.atguigu.atcrowdfunding.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.atguigu.atcrowdfunding.api.AdminService;
import com.atguigu.atcrowdfunding.bean.TAdmin;
import com.atguigu.atcrowdfunding.bean.TAdminExample;
import com.atguigu.atcrowdfunding.bean.TAdminExample.Criteria;
import com.atguigu.atcrowdfunding.bean.TMenu;
import com.atguigu.atcrowdfunding.bean.TMenuExample;
import com.atguigu.atcrowdfunding.exception.EmailExistException;
import com.atguigu.atcrowdfunding.exception.LoginacctExistException;
import com.atguigu.atcrowdfunding.mapper.TAdminMapper;
import com.atguigu.atcrowdfunding.mapper.TMenuMapper;
import com.atguigu.atcrowdfunding.utils.AppUtils;

@Service
public class AdminServiceImpl implements AdminService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	TAdminMapper adminMapper;
	
	@Autowired
	TMenuMapper menuMapper;
	
	@Override
	public TAdmin login(String username, String password) {
		
		// TODO Auto-generated method stub
		//执行登录
		
		String pwd = AppUtils.getDigestPwd(password);
		
		//xxxxByExample结尾的：代表带复杂条件的Crud；
		//xxxByPrimaryKey结尾的；代表用主键进行crud；
		//xxxxSelective：常见于insert和update；代表有选择的插入更新；只更新不为null的字段
		//
		TAdminExample example = new TAdminExample();
		
		Criteria criteria = example.createCriteria();
		criteria.andLoginacctEqualTo(username).andUserpswdEqualTo(pwd);
		
		
		List<TAdmin> list = adminMapper.selectByExample(example);
		
		
		return list!=null&&list.size()==1?list.get(0):null;
	}

	@Override
	public List<TMenu> listMenus() {
		// TODO Auto-generated method stub
		//1、查出父菜单
		TMenuExample example = new TMenuExample();
		example.createCriteria().andPidEqualTo(0);
		
		
		
		List<TMenu> parentMenus = menuMapper.selectByExample(example);
		
		//2、把这些父菜单的子菜单找到
		for (TMenu menu :parentMenus) {
			
			TMenuExample example2 = new TMenuExample();
			//父菜单的id就是子菜单的pid
			example2.createCriteria().andPidEqualTo(menu.getId());
			List<TMenu> childMenus = menuMapper.selectByExample(example2);
			
			//保存起来
			menu.setChilds(childMenus);
		}
		
		
		return parentMenus;
	}

	@Override
	public List<TAdmin> listAllAdmin() {
		
		return adminMapper.selectByExample(null);
	}

	@Override
	public List<TAdmin> listAllAdminByCondition(String condition) {
		// TODO Auto-generated method stub
		TAdminExample example = new TAdminExample();
		if(!StringUtils.isEmpty(condition)) {
			
			//所有and条件都放在第一个Criteria
			Criteria c0 = example.createCriteria();
			c0.andLoginacctLike("%"+condition+"%");
			
			//有or条件就创建新的Criteria
			Criteria c1 = example.createCriteria();
			c1.andUsernameLike("%"+condition+"%");
			
			Criteria c2 = example.createCriteria();
			c2.andEmailLike("%"+condition+"%");
			
			
			example.or(c1);
			example.or(c2);
			
		}
		
		return adminMapper.selectByExample(example);
	}

	@Override
	public TAdmin getAdminById(Integer id) {
		// TODO Auto-generated method stub
		return adminMapper.selectByPrimaryKey(id);
	}

	@Override
	public void updateAdmin(TAdmin admin) {
		// TODO Auto-generated method stub
		//有选择的更新
		adminMapper.updateByPrimaryKeySelective(admin);
	}

	//新增admin用户
	@Override
	public void saveAdmin(TAdmin admin) {
		// TODO Auto-generated method stub
		//1、检查邮箱是否被占用
		boolean email = checkEmail(admin);
		if(!email) {
			//邮箱不可用
//			validEmial =false;
			throw new EmailExistException();
		}
		
		
		
		//2、检查账号是否可用
		boolean loginacct = checkLoginacct(admin);
		if(!loginacct) {
			//账号存在
//			validloginacct =false;
			throw new LoginacctExistException();
		}
		
//		if(validloginacct==false&&validEmial==false) {
//			throw new LoginacctAndEmailExistException();
//		}else if(validloginacct==false){
//			throw new LoginacctExistException();
//		}else if(validEmial == false) {
//			throw new EmailExistException();
//		}
		
		
		//3、保存即可
		admin.setUserpswd(AppUtils.getDigestPwd("123456"));
		admin.setCreatetime(AppUtils.getCurrentTimeStr());
		adminMapper.insertSelective(admin);
		logger.info("{}用户保存成功...",admin.getLoginacct());
		
		
	}

	@Override
	public boolean checkLoginacct(TAdmin admin) {
		// TODO Auto-generated method stub
		TAdminExample example = new TAdminExample();
		example.createCriteria().andLoginacctEqualTo(admin.getLoginacct());
		
		long l = adminMapper.countByExample(example);
		
		return l==0L?true:false;
	}
	
	@Override
	public boolean checkEmail(TAdmin admin) {
		// TODO Auto-generated method stub
		TAdminExample example = new TAdminExample();
		example.createCriteria().andEmailEqualTo(admin.getEmail());
		long count = adminMapper.countByExample(example);
		return count==0L?true:false;
	}

	@Override
	public void deleteAdminById(Integer id) {
		// TODO Auto-generated method stub
		adminMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 按照用户id查询自己的菜单
	 */
	@Override
	public List<TMenu> listYourMenus(Integer id) {
		List<TMenu> parentsMenu = new ArrayList<TMenu>();
		// TODO Auto-generated method stub
		List<TMenu> myMenus = menuMapper.getMyMenus(id);
		//组装所有这些菜单的父子关系；
		for (TMenu tMenu : myMenus) {
			if(tMenu.getPid() == 0) {
				//父菜单
				parentsMenu.add(tMenu);
			}
		}
		
		//找到每一个父菜单的子菜单
		for (TMenu pMenu : parentsMenu) {
			//1、为当前父菜单找子菜单
			for(TMenu childMenu:myMenus) {
				if(childMenu.getPid() == pMenu.getId()) {
					pMenu.getChilds().add(childMenu);
				}
			}
		}
		
		
		return parentsMenu;
	}
	
	

}
