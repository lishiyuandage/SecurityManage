package com.bigbrotherlee.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeRepository {
	
	/**
	 * 得到验证码
	 * @param request
	 * @param type
	 * @return
	 */
	ValidateCode get(ServletWebRequest request,ValidateCodeType type); 
	
	/**
	 * 保持验证码
	 * @param request
	 * @param code
	 * @param type
	 */
	void save(ServletWebRequest request,ValidateCode code,ValidateCodeType type);
	
	
	/**
	 * 移除验证码
	 * @param request
	 * @param type
	 */
	void remove(ServletWebRequest request,ValidateCodeType type);
}
