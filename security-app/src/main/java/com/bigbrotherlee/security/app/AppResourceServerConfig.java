package com.bigbrotherlee.security.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.social.security.SpringSocialConfigurer;

import com.bigbrotherlee.security.core.authentication.phone.SmsCodeSecurityConfig;
import com.bigbrotherlee.security.core.properties.SecurityConstants;
import com.bigbrotherlee.security.core.properties.SecurityProperties;

@Configuration
@EnableResourceServer
public class AppResourceServerConfig extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private SmsCodeSecurityConfig smsCodeSecurityConfig;
	@Autowired
	private SpringSocialConfigurer socialSecurityConfig;
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		applyPasswordAuthenticationConfig(http);

		http.apply(smsCodeSecurityConfig).and().apply(socialSecurityConfig)
				.and()
				.authorizeRequests()
				.antMatchers(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/*",
						SecurityConstants.DEFAULT_UNAUTHENTICATION_URL,
						SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_PHONE,
						securityProperties.getBrowser().getLoginPage(), securityProperties.getBrowser().getSignUpPage(),
						SecurityConstants.DEFAULT_SESSION_INVALID_URL)
				.permitAll().anyRequest().authenticated().and().csrf().disable();
	}
	@Autowired
	protected AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	protected AuthenticationFailureHandler authenticationFailureHandler;
	
	protected void applyPasswordAuthenticationConfig(HttpSecurity http) throws Exception {
		http.formLogin()
			.loginPage(SecurityConstants.DEFAULT_UNAUTHENTICATION_URL)
			.loginProcessingUrl(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM)
			.successHandler(authenticationSuccessHandler)
			.failureHandler(authenticationFailureHandler);
	}
}
