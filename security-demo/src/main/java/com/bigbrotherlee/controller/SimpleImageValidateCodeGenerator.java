package com.bigbrotherlee.controller;

import java.awt.image.BufferedImage;

import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.validate.code.img.DefaultValidateCodeGenerator;
import com.bigbrotherlee.security.core.validate.code.img.ImageCode;

@Component("imageValidateCodeGenerator")
public class SimpleImageValidateCodeGenerator extends DefaultValidateCodeGenerator {
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public ImageCode generate(ServletWebRequest request) {
		int width=ServletRequestUtils.getIntParameter(request.getRequest(), "width", securityProperties.getCode().getImage().getWidth());
		int height=ServletRequestUtils.getIntParameter(request.getRequest(), "height", securityProperties.getCode().getImage().getHeight());
		int expireIn=securityProperties.getCode().getImage().getExpireIn();
		int number1=RandomUtils.nextInt(100);
		int number2=RandomUtils.nextInt(100);
		int answer=-101;
		String code=null;
		if(RandomUtils.nextBoolean()) {
			code=number1+"+"+number2;
			answer=number1+number2;
		}else {
			code=number1+"-"+number2;
			answer=number1-number2;
		}
		char [] codeArray=code.toCharArray();
		BufferedImage image=getImg(codeArray,width,height);
		return new ImageCode(image,Integer.toString(answer), expireIn);
	}

}
