package com.atguigu.atcrowdfunding.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.DigestUtils;

public class AppUtils {
	
	/**
	 * 密码加密保存到数据库
	 * @param str
	 * @return
	 */
	public static String getDigestPwd(String str) {
		String string = str;
		for(int i = 0;i<100;i++) {
			string = DigestUtils.md5DigestAsHex(string.getBytes());
		}
		return string;
	}
	
	public static String getCurrentTimeStr() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}
	
	public static String getCurrentTimeStr(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	
	public static String getCurrentTimeStr(Date date,String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	

}
