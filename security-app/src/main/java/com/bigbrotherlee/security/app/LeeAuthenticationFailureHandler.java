package com.bigbrotherlee.security.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.bigbrotherlee.security.core.properties.LoginType;
import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LeeAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	
	//登录失败
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
			throws IOException, ServletException {
		logger.info("登录失败——"+e);
		response.setStatus(500);
		if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType())) {
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(e.getMessage())));
		}else {
			super.onAuthenticationFailure(request, response, e);
		}
	}
}
