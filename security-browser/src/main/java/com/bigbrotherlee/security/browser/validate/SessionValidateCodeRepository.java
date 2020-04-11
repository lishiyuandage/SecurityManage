package com.bigbrotherlee.security.browser.validate;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.validate.code.AbstractValidateCodeProcessor;
import com.bigbrotherlee.security.core.validate.code.ValidateCode;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeRepository;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeType;

@Component("validateCodeRepository")
@ConditionalOnMissingBean(name = "validateCodeRepository")
public class SessionValidateCodeRepository implements ValidateCodeRepository {
	
	private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();
	
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
		return (ValidateCode) sessionStrategy.getAttribute(request, getSessionKey(request, type));
	}

	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
		sessionStrategy.setAttribute(request, getSessionKey(request, type),code);
	}

	@Override
	public void remove(ServletWebRequest request, ValidateCodeType type) {
		sessionStrategy.removeAttribute(request, getSessionKey(request, type));
	}
	
	/**
	 * 构建验证码放入session时的key
	 * @param request
	 * @return
	 */
	private String getSessionKey(ServletWebRequest request,ValidateCodeType type) {
		return AbstractValidateCodeProcessor.SESSION_KEY_PREFIX + type.toString().toUpperCase();
	}
}
