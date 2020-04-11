package com.bigbrotherlee.security.app;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.bigbrotherlee.security.core.properties.LoginType;
import com.bigbrotherlee.security.core.properties.SecurityProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LeeAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private SecurityProperties securityProperties;
	@Autowired
	private ClientDetailsService clientDetailsService;
	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;
	
	//登录成功
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String header=request.getHeader("Authorization");
		
		if(header==null || !header.startsWith("Basic")) {
			throw new UnapprovedClientAuthenticationException("Authorization 请求头错误");//如果Authorization请求头不正确则抛异常
		}
		
		String [] token=extractAndDecodeHeader(header, request);
		String clientId=token[0];
		String clientSecret=token[1];
		
		ClientDetails clientDetails=clientDetailsService.loadClientByClientId(clientId);
		
		if(clientDetails==null) {
			throw new UnapprovedClientAuthenticationException("client_id 对应的信息不存在："+clientId);
		}else if(StringUtils.equals(clientSecret, clientDetails.getClientSecret())) {
			throw new UnapprovedClientAuthenticationException("client_secret 错误");
		}
		
		TokenRequest tokenRequest=new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
		OAuth2Request auth2Request=tokenRequest.createOAuth2Request(clientDetails);
		OAuth2Authentication auth2Authentication=new OAuth2Authentication(auth2Request, authentication);
		
		OAuth2AccessToken accessToken=authorizationServerTokenServices.createAccessToken(auth2Authentication);
		logger.info("登录成功——"+accessToken);
		response.setContentType("application/json;charrset=UTF-8");
		response.getWriter().write(objectMapper.writeValueAsString(accessToken));
	}
	/**
	 * Decodes the header into a username and password.
	 * @throws BadCredentialsException if the Basic header is not present or is not valid
	 * Base64
	 */
	private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
			throws IOException {

		byte[] base64Token = header.substring(6).getBytes("UTF-8");
		byte[] decoded;
		try {
			decoded = Base64.decode(base64Token);
		}
		catch (IllegalArgumentException e) {
			throw new BadCredentialsException(
					"Failed to decode basic authentication token");
		}

		String token = new String(decoded, "UTF-8");

		int delim = token.indexOf(":");

		if (delim == -1) {
			throw new BadCredentialsException("Invalid basic authentication token");
		}
		return new String[] { token.substring(0, delim), token.substring(delim + 1) };
	}


}
