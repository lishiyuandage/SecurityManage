package com.bigbrotherlee.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;


/**
 * 取用户数据
 * @author li
 * @createdate 2019年4月27日
 */
@Component
public class LeeUserDetailsService implements UserDetailsService,SocialUserDetailsService {
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		logger.info("登录名："+username+"----");
		return buildUser(username);//最后一个是权限列表
	}

	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
		logger.info("登录名："+userId+"----");
		return buildUser(userId);
	}

	private SocialUserDetails buildUser(String userId) {
		String password=passwordEncoder.encode("123456");
		//是否删除，用户是否过期，用户密码是否过期，账户是否冻结（是否锁定）
		return new SocialUser(userId, password,true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER"));//最后一个是权限列表
	}

}
