package com.bigbrotherlee.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="com.bigbrotherlee.security")
public class SecurityProperties {
	
	private BrowserProperties browser=new BrowserProperties();//登录注册
	private SocialProperties social=new SocialProperties();//第三方登录注册
	private ValidateCodeProperties code=new ValidateCodeProperties();//验证码
	
	public SocialProperties getSocial() {
		return social;
	}

	public void setSocial(SocialProperties social) {
		this.social = social;
	}
	
	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}

	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}
	
}
