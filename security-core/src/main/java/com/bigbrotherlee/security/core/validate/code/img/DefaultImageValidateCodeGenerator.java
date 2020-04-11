package com.bigbrotherlee.security.core.validate.code.img;

import java.awt.image.BufferedImage;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.properties.SecurityProperties;

/**
 * @author lee
 * 默认的图片验证码生成器
 */
public class DefaultImageValidateCodeGenerator extends DefaultValidateCodeGenerator {
	
	private SecurityProperties securityProperties;
	
	
	@Override
	public ImageCode generate(ServletWebRequest request) {
		int width=ServletRequestUtils.getIntParameter(request.getRequest(), "width", securityProperties.getCode().getImage().getWidth());
		int height=ServletRequestUtils.getIntParameter(request.getRequest(), "height", securityProperties.getCode().getImage().getHeight());
		int expireIn=securityProperties.getCode().getImage().getExpireIn();
		String code=RandomStringUtils.randomNumeric(securityProperties.getCode().getImage().getLength());
		char [] codeArray=code.toCharArray();
		BufferedImage image=getImg(codeArray,width,height);
		return new ImageCode(image,code, expireIn);
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	
}
