package com.bigbrotherlee.security.core.validate.code.img;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.validate.code.AbstractValidateCodeProcessor;

@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {
	
	@Override
	protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
		ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
	}

}
