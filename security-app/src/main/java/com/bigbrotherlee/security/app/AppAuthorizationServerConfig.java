package com.bigbrotherlee.security.app;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import com.bigbrotherlee.security.core.authentication.AbstractChannelSecurityConfig;

@Configuration
@EnableAuthorizationServer
public class AppAuthorizationServerConfig extends AbstractChannelSecurityConfig{
	
}
