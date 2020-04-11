package com.bigbrotherlee.security.core.validate.code.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeGenerator;
import com.bigbrotherlee.security.core.validate.code.img.DefaultImageValidateCodeGenerator;
import com.bigbrotherlee.security.core.validate.code.sms.CodeSender;
import com.bigbrotherlee.security.core.validate.code.sms.DefaultSmsCodeGenerator;
import com.bigbrotherlee.security.core.validate.code.sms.DefaultSmsCodeSender;

@Configuration
public class ValidateBeanConfig {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * 图片验证码生成器的bean的名字是 “imageValidateCodeGenerator”，如果用户没有自定义，则调用默认的生成逻辑
	 */
	@Bean
	@ConditionalOnMissingBean(name="imageValidateCodeGenerator")
	public ValidateCodeGenerator imageValidateCodeGenerator() {
		DefaultImageValidateCodeGenerator imageCodeGenerator=new DefaultImageValidateCodeGenerator();
		imageCodeGenerator.setSecurityProperties(securityProperties);
		return imageCodeGenerator;
	}
	
	/**
	 * 短信验证码生成器的bean的名字是 “smsValidateCodeGenerator”，如果用户没有自定义，则调用默认的生成逻辑
	 */
	@Bean
	@ConditionalOnMissingBean(name="smsValidateCodeGenerator")
	public ValidateCodeGenerator smsValidateCodeGenerator() {
		DefaultSmsCodeGenerator smsCodeGenerator=new DefaultSmsCodeGenerator();
		smsCodeGenerator.setSecurityProperties(securityProperties);
		return smsCodeGenerator;
	}
	
	/**
	 * 短信验证码发送器的bean的名字是 “smsCodeSender”，如果用户没有自定义，则调用默认的生成逻辑，<br><strong>你应该重写一个发送器放到spring容器里</strong>
	 */
	@Bean
	@ConditionalOnMissingBean(name="smsCodeSender")
	public CodeSender smsCodeSender() {
		return new DefaultSmsCodeSender();
	}	
}
