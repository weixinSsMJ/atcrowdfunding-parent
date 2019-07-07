package com.atguigu.atcrowdfunding.exception;

/**
 * 邮箱已经存在异常
 * @author lfy
 *
 */
public class EmailExistException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EmailExistException() {
		super("邮箱已经存在");
		// TODO Auto-generated constructor stub
	}

	public EmailExistException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}
	
	

}
