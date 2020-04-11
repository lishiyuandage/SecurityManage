package com.bigbrotherlee.security.core.authentication.phone;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider {
	
	private UserDetailsService userDetailsService;
	
	/**
	 * 认证逻辑
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UserDetails user=userDetailsService.loadUserByUsername((String)authentication.getPrincipal());
		if(user==null) {
			throw new InternalAuthenticationServiceException("无法获取用户信息");
		}
		SmsCodeAuthenticationToken authenticationToken=new SmsCodeAuthenticationToken(user, user.getAuthorities());
		authenticationToken.setDetails(authentication.getDetails());
		
		return authenticationToken;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		/*
		 * 注意
		 * clazz.isAssignableFrom(class1) 判定此 Class 对象所表示的类或接口与指定的 Class 参数所表示的类或接口是否相同，
		 * 或是否是其超类或超接口。如果是则返回 true；否则返回 false。
		 * 如果该 Class 表示一个基本类型，且指定的 Class 参数正是该 Class 对象，则该方法返回 true；否则返回 false。 
		 */
		return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);//判断是否是这个类型
	}

	public UserDetailsService getUserDetailsService() {
		return userDetailsService;
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

}
