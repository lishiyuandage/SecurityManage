package com.bigbrotherlee.security.core.validate.code;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.properties.SecurityConstants;

/**
 * 验证码获取类
 * @author lee
 */
@RestController
public class ValidateCodeController {
	
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	@GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX+"/{type}")
	public void  createCode(@PathVariable String type,HttpServletResponse response,HttpServletRequest request) throws Exception {
		validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request,response));
	}
}
