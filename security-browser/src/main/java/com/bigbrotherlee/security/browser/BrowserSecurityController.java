package com.bigbrotherlee.security.browser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.browser.support.SocialUserInfo;
import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.bigbrotherlee.security.core.support.SimpleResponse;

@RestController
public class BrowserSecurityController {
	private Logger logger=LoggerFactory.getLogger(BrowserSecurityController.class);
	
	private RequestCache requestCache=new HttpSessionRequestCache();
	
	private RedirectStrategy redirectStrategy=new DefaultRedirectStrategy();
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ProviderSignInUtils providerSignInUtils;
	
	@RequestMapping("/loginmatch")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)//状态码为401
	public SimpleResponse loginMatch(HttpServletRequest request,HttpServletResponse response) throws Exception {
		SavedRequest savedRequest=requestCache.getRequest(request, response);
		
		if(savedRequest != null) {
			String targetUrl=savedRequest.getRedirectUrl();
			logger.info("targetUrl（要跳转的页面为）->"+targetUrl);
			if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")) {
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}
		}
		
		return new SimpleResponse("服务需要用户认证");
	}
	
	@GetMapping("socialinfo")
	public SocialUserInfo getSocialUserInfo(HttpServletRequest request) {
		SocialUserInfo info=new SocialUserInfo();
		Connection<?> connect= providerSignInUtils.getConnectionFromSession(new ServletWebRequest(request));
		info.setHeadimg(connect.getImageUrl());
		info.setNickname(connect.getDisplayName());
		info.setProviderId(connect.getKey().getProviderId());
		info.setProviderUserId(connect.getKey().getProviderUserId());
		return info;
	}
	@GetMapping("/session/invalid")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public SimpleResponse invalid() {
		return new SimpleResponse("session失效了");
	}
}
