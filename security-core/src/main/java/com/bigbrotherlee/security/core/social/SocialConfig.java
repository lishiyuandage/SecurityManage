package com.bigbrotherlee.security.core.social;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.security.SpringSocialConfigurer;

import com.bigbrotherlee.security.core.properties.SecurityProperties;

@Configuration
@EnableSocial
public class SocialConfig extends SocialConfigurerAdapter {
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired(required = false)//非必须
	private ConnectionSignUp connectionSignUp;
	
	@Autowired
	private DataSource dataSource;
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
		JdbcUsersConnectionRepository jdbcUsersConnectionRepository=new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator,Encryptors.noOpText());
		if(connectionSignUp!=null) {
			jdbcUsersConnectionRepository.setConnectionSignUp(connectionSignUp);//注入静默注册器
		}
		return jdbcUsersConnectionRepository;//Encryptors.noOpText()表示不加密
	}
	@Bean
	public SpringSocialConfigurer socialSecurityConfig() {
		String filterUrl=securityProperties.getSocial().getFilterProcessesUrl();
		LeeSpringSocialConfigurer springSocialConfigurer=
				new LeeSpringSocialConfigurer(filterUrl);//回调url
		springSocialConfigurer.signupUrl(securityProperties.getBrowser().getSignUpPage());//注册页面
		return springSocialConfigurer;
	}
	
	@Bean
	public ProviderSignInUtils providerSignInUtils(ConnectionFactoryLocator connectionFactoryLocator) {
		return new ProviderSignInUtils(connectionFactoryLocator, getUsersConnectionRepository(connectionFactoryLocator));
	}
}
