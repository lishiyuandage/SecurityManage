package com.bigbrotherlee.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码处理器，处理不同校验码的处理逻辑
 * 
 * @author lee
 */
public interface ValidateCodeProcessor {
	
	String SESSION_KEY_PREFIX="SESSION_KEY_FOR_CODE_";
	
	/**
	 * 校验码生成器
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request)throws Exception;
	/**
	 * 校验验证码
	 * @param servletWebRequest
	 */
	void validate(ServletWebRequest servletWebRequest);
}
