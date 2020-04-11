package com.bigbrotherlee.security.browser.logout;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.support.SimpleResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LeeLogoutSuccessHandler implements LogoutSuccessHandler {
	private static final Logger log = LoggerFactory.getLogger(LeeLogoutSuccessHandler.class);

	private ObjectMapper objectMapper=new ObjectMapper();
	private SecurityProperties securityProperties;
	
	public LeeLogoutSuccessHandler(SecurityProperties securityProperties) {
		super();
		this.securityProperties = securityProperties;
	}

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		log.info("退出登录");
		if(StringUtils.isBlank(securityProperties.getBrowser().getSignOutPage())) {
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse("退出成功")));
		}else {
			response.sendRedirect(securityProperties.getBrowser().getSignOutPage());
		}
	}

}
