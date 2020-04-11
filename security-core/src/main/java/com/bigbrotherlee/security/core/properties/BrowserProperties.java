package com.bigbrotherlee.security.core.properties;

import com.bigbrotherlee.security.core.properties.LoginType;
public class BrowserProperties {
	
	private String loginPage ="/default-login.html";
	private String signUpPage="/default-signup.html";
	private String signOutPage;
	private int rememberMeTimeOut=3600*24*7; //记住我 时长 一周
	private SessionProperties session=new SessionProperties();
	
	public int getRememberMeTimeOut() {
		return rememberMeTimeOut;
	}

	public void setRememberMeTimeOut(int rememberMeTimeOut) {
		this.rememberMeTimeOut = rememberMeTimeOut;
	}

	private LoginType loginType=LoginType.JSON;
	
	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public String getSignUpPage() {
		return signUpPage;
	}

	public void setSignUpPage(String signUpPage) {
		this.signUpPage = signUpPage;
	}

	public SessionProperties getSession() {
		return session;
	}

	public void setSession(SessionProperties session) {
		this.session = session;
	}

	public String getSignOutPage() {
		return signOutPage;
	}

	public void setSignOutPage(String signOutPage) {
		this.signOutPage = signOutPage;
	}

}
