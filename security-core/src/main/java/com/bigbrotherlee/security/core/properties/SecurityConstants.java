package com.bigbrotherlee.security.core.properties;

/**
 * 常量类
 * @author lee
 *
 */
public interface SecurityConstants {
	
	/**
	 * 默认的处理验证码的url前缀
	 */
	public static final String DEFAULT_VALIDATE_CODE_URL_PREFIX = "/code";
	
	/**
	 * 未认证
	 */
	public static final String DEFAULT_UNAUTHENTICATION_URL="/loginmatch";
	
	/**
	 * 默认的用户名密码登录请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_FORM = "/login";
	/**
	 * 默认的手机验证码登录请求处理url
	 */
	public static final String DEFAULT_LOGIN_PROCESSING_URL_PHONE = "/login/phone";
	
	/**
	 * 默认登录页面
	 * 
	 * @see SecurityController
	 */
	public static final String DEFAULT_LOGIN_PAGE_URL = "/default-login.html";
	/**
	 * 验证图片验证码时，http请求中默认的携带图片验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_IMAGE = "image";
	/**
	 * 验证短信验证码时，http请求中默认的携带短信验证码信息的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_CODE_SMS = "sms";
	/**
	 * 发送短信验证码 或 验证短信验证码时，传递手机号的参数的名称
	 */
	public static final String DEFAULT_PARAMETER_NAME_PHONE = "phone";
	/**
	 * session失效默认的跳转地址
	 */
	public static final String DEFAULT_SESSION_INVALID_URL = "/session/invalid";
	
	public static final String DEFAULT_LOGOUT_URL="/logout";
}
