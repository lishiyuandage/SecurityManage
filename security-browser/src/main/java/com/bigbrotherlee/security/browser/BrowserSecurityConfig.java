package com.bigbrotherlee.security.browser;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.session.InvalidSessionStrategy;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.social.security.SpringSocialConfigurer;

import com.bigbrotherlee.security.core.authentication.AbstractChannelSecurityConfig;
import com.bigbrotherlee.security.core.authentication.phone.SmsCodeSecurityConfig;
import com.bigbrotherlee.security.core.properties.SecurityConstants;
import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeSecurityConfig;

@Configuration
public class BrowserSecurityConfig extends AbstractChannelSecurityConfig{
	
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ValidateCodeSecurityConfig validateCodeSecurityConfig;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private SmsCodeSecurityConfig smsCodeSecurityConfig;
	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;
	@Autowired
	private InvalidSessionStrategy invalidSessionStrategy;
	@Autowired
	private SessionInformationExpiredStrategy sessionInformationExpiredStrategy;
	@Autowired
	private LogoutSuccessHandler logoutSuccessHandler;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		applyPasswordAuthenticationConfig(http);
		
		http.apply(validateCodeSecurityConfig)
				.and()
			.apply(smsCodeSecurityConfig)
				.and()
			.apply(socialSecurityConfig)
				.and()
			.rememberMe()
				.tokenRepository(persistentTokenRepository())
				.tokenValiditySeconds(securityProperties.getBrowser().getRememberMeTimeOut())
				.userDetailsService(userDetailsService)
				.and()
			.sessionManagement()
				.invalidSessionStrategy(invalidSessionStrategy)
				.maximumSessions(securityProperties.getBrowser().getSession().getMaximumSessions())
				.maxSessionsPreventsLogin(securityProperties.getBrowser().getSession().isMaxSessionsPreventsLogin())
				.expiredSessionStrategy(sessionInformationExpiredStrategy)
				.and()
				.and()
			.logout()
				.logoutUrl(SecurityConstants.DEFAULT_SESSION_INVALID_URL)
				.logoutSuccessHandler(logoutSuccessHandler)
				.deleteCookies("JSESSIONID")
				.and()
			.authorizeRequests()
				.antMatchers(
					SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/*",
					SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
					SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_PHONE,
					securityProperties.getBrowser().getLoginPage(),
					securityProperties.getBrowser().getSignUpPage(),
					SecurityConstants.DEFAULT_SESSION_INVALID_URL)
					.permitAll()
				.anyRequest()
				.authenticated()
				.and()
			.csrf().disable();
	}
	
	/**
	 * @return remember-me使用
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);
//		tokenRepository.setCreateTableOnStartup(true);//自动建表
		return tokenRepository;
	}
	
}
