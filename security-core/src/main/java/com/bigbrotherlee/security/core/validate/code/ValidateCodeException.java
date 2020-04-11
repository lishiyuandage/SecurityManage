package com.bigbrotherlee.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author lee
 * 验证码异常
 */
public class ValidateCodeException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 22435715120792608L;

	public ValidateCodeException(String msg) {
		super(msg);
		
	}

}
