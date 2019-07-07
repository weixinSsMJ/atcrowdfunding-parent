package com.atguigu.atcrowdfunding.exception;

/**
 * 	账号已经存在异常
 * @author lfy
 *
 */
public class LoginacctExistException extends RuntimeException {

	
	private static final long serialVersionUID = 1L;

	public LoginacctExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public LoginacctExistException() {
		super("账号已经存在");
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
