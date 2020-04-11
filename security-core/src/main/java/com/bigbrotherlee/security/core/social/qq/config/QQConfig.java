package com.bigbrotherlee.security.core.social.qq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.social.SocialAutoConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.web.servlet.View;

import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.social.LeeConnectionSuccessView;
import com.bigbrotherlee.security.core.social.qq.connect.QQConnectionFactory;

@Configuration
@ConditionalOnProperty(prefix = "com.bigbrotherlee.security.social.qq",name = "app-id")
public class QQConfig extends SocialAutoConfigurerAdapter {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	protected ConnectionFactory<?> createConnectionFactory() {
		String providerId=securityProperties.getSocial().getQq().getProviderId();
		String appId=securityProperties.getSocial().getQq().getAppId();
		String appSecret=securityProperties.getSocial().getQq().getAppSecret();
		return new QQConnectionFactory(providerId, appId, appSecret);
	}
	
	
	@Bean({"connect/qqConnect","connect/qqConnected"})
	@ConditionalOnBean(name="qqConnectionView")
	public View qqConnectionView() {
		return new LeeConnectionSuccessView();
	}
}
