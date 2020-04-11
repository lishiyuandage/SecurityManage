package com.bigbrotherlee.security.core.authentication.phone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import com.bigbrotherlee.security.core.properties.SecurityConstants;

/**
 * 手机号登录过滤器
 * @author lee
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String FORM_PHONE_KEY = "phone";

	private String phoneParameter = FORM_PHONE_KEY;
	private boolean postOnly = true;


	public SmsCodeAuthenticationFilter() {
		super(new AntPathRequestMatcher(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_PHONE, "POST"));
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if (postOnly && !request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
		}

		String phone = obtainPhone(request);

		if (phone == null) {
			phone = "";
		}

		phone = phone.trim();

		SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(phone);

		setDetails(request, authRequest);

		return this.getAuthenticationManager().authenticate(authRequest);
	}

	

	
	/**
	 * 得到手机号
	 * @param request
	 * @return
	 */
	protected String obtainPhone(HttpServletRequest request) {
		return request.getParameter(phoneParameter);
	}

	/**
	 * 保持用户信息
	 */
	protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
		authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
	}

	/**
	 * @param usernameParameter the parameter name. Defaults to "phone".
	 */
	public void setPhoneParameter(String phoneParameter) {
		Assert.hasText(phoneParameter, "Username parameter must not be empty or null");
		this.phoneParameter = phoneParameter;
	}


	/**
	 * 是否只处理post
	 */
	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getPhoneParameter() {
		return phoneParameter;
	}
}
