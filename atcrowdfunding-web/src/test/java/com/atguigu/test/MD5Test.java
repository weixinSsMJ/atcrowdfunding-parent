package com.atguigu.test;

import org.junit.Test;
import org.springframework.util.DigestUtils;

public class MD5Test {
	
	
	@Test
	public void md5Test() {
		
		//e10adc3949ba59abbe56e057f20f883e
		//e10adc3949ba59abbe56e057f20f883e
		String pwd2 = "123456";
		for (int i = 0; i < 100; i++) {
			pwd2 = pwd(pwd2);
		}
		System.out.println(pwd2);
		
		
	
		
		
		//SHA1、SHA256、SHA512；盐值加密；123456+djsakldjasklj;
		
		

		
	}
	
	public String pwd(String str) {
		return DigestUtils.md5DigestAsHex(str.getBytes());
	}

}
