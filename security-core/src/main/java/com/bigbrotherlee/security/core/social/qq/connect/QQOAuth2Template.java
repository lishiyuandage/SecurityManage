package com.bigbrotherlee.security.core.social.qq.connect;

import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class QQOAuth2Template extends OAuth2Template {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());

	public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
		super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
		
		setUseParametersForClientAuthentication(true);//保证带有client_id和client_secret
	}
	
	@Override
	protected RestTemplate createRestTemplate() {
		RestTemplate t=super.createRestTemplate();
		t.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));//加入html/text的解析器
		return t;
	}
	
	//重写以适应qq的回传数据
	@Override
	protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
		String responseStr=getRestTemplate().postForObject(accessTokenUrl, parameters, String.class);
		
		logger.info(responseStr);
		
		String [] info=StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr, "&");
		
		String accessToken=StringUtils.substringAfter(info[0], "=");
		Long expiresIn=new Long(StringUtils.substringAfter(info[1], "="));
		String refreshToken=StringUtils.substringAfter(info[2], "=");
		
		return new AccessGrant(accessToken, null, refreshToken, expiresIn);
	}
	
}
