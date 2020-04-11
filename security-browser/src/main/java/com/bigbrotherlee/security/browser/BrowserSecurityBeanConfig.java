package com.bigbrotherlee.security.browser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import com.bigbrotherlee.security.browser.logout.LeeLogoutSuccessHandler;
import com.bigbrotherlee.security.browser.session.LeeExpiredSessionStrategy;
import com.bigbrotherlee.security.browser.session.LeeInvalidSessionStrategy;
import com.bigbrotherlee.security.core.properties.SecurityProperties;

/**
 * @author zhailiang
 *
 */
@Configuration
public class BrowserSecurityBeanConfig {

	@Autowired
	private SecurityProperties securityProperties;
	
	@Bean
	@ConditionalOnMissingBean(InvalidSessionStrategy.class)
	public InvalidSessionStrategy invalidSessionStrategy(){
		return new LeeInvalidSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}
	
	@Bean
	@ConditionalOnMissingBean(SessionInformationExpiredStrategy.class)
	public SessionInformationExpiredStrategy sessionInformationExpiredStrategy(){
		return new LeeExpiredSessionStrategy(securityProperties.getBrowser().getSession().getSessionInvalidUrl());
	}
	
	@Bean
	@ConditionalOnMissingBean(name="logoutSuccessHandler")//退出成功处理器
	public LogoutSuccessHandler logoutSuccessHandler(){
		return new LeeLogoutSuccessHandler(securityProperties);
	}
	
}