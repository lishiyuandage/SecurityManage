package com.bigbrotherlee.security.core.validate.code.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.validate.code.ValidateCode;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeGenerator;

/**
 * 默认短信验证码生成器
 * @author lee
 */
public class DefaultSmsCodeGenerator implements ValidateCodeGenerator{
	
	private SecurityProperties securityProperties;

	@Override
	public ValidateCode generate(ServletWebRequest request) {
		int expireIn=securityProperties.getCode().getSms().getExpireIn();
		String code=RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
		return new ValidateCode(code, expireIn);
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
}
