package com.bigbrotherlee.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bigbrotherlee.security.core.properties.SecurityConstants;
import com.bigbrotherlee.security.core.properties.SecurityProperties;

@Component("validateCodeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{
	
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Autowired
	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	private Map<String,ValidateCodeType> urlMap=new HashMap<>();
	
	
	private AntPathMatcher pathMatcher=new AntPathMatcher();
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_FORM, ValidateCodeType.IMAGE);
		addUrlToMap(securityProperties.getCode().getImage().getUrls(),ValidateCodeType.IMAGE);
		urlMap.put(SecurityConstants.DEFAULT_LOGIN_PROCESSING_URL_PHONE, ValidateCodeType.SMS);
		addUrlToMap(securityProperties.getCode().getSms().getUrls(),ValidateCodeType.SMS);
	}
	
	/**
	 * 将配置的url放到urlMap中
	 * @param urlString
	 * @param type
	 */
	private void addUrlToMap(String urlString,ValidateCodeType type) {
		if(StringUtils.isNotBlank(urlString)) {
			String [] urls=StringUtils.splitByWholeSeparatorPreserveAllTokens(urlString, ",");
			for(String url:urls) {
				urlMap.put(url, type);
			}
		}
	}
	
	/**
	 * 检验验证码的逻辑
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		ValidateCodeType type = getValidateCodeType(request);
		if (type != null) {
			logger.info("校验请求(" + request.getRequestURI() + ")中的验证码,验证码类型" + type);
			try {
				validateCodeProcessorHolder.findValidateCodeProcessor(type).validate(new ServletWebRequest(request, response));
				logger.info("验证码校验通过");
			} catch (Exception exception) {
				authenticationFailureHandler.onAuthenticationFailure(request, response, new ValidateCodeException(exception.getMessage()));
				return;
			}
		}
		filterChain.doFilter(request, response);
	}
	/**
	 * 获取校验码的类型，如果当前请求不需要校验，则返回null
	 * 
	 * @param request
	 * @return
	 */
	private ValidateCodeType getValidateCodeType(HttpServletRequest request) {
		ValidateCodeType result = null;
		if (!StringUtils.equalsIgnoreCase(request.getMethod(), "get")) {
			Set<String> urls = urlMap.keySet();
			for (String url : urls) {
				if (pathMatcher.match(url, request.getRequestURI())) {
					result = urlMap.get(url);
				}
			}
		}
		return result;
	}
	
}
