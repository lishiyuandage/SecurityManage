package com.bigbrotherlee.security.core.social.qq.connect;

import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

import com.bigbrotherlee.security.core.social.qq.api.QQApi;
import com.bigbrotherlee.security.core.social.qq.api.QQApiImpl;

public class QQServiceProvider extends AbstractOAuth2ServiceProvider<QQApi> {
	
	private String appId;
	
	private static final String AUTHORIZE_URL="https://graph.qq.com/oauth2.0/authorize";
	
	private static final String ACCESSTOKEN_URL="https://graph.qq.com/oauth2.0/token";

	public QQServiceProvider(String appId,String appSecret) {
		super(new QQOAuth2Template(appId, appSecret, AUTHORIZE_URL, ACCESSTOKEN_URL));
		this.appId=appId;
	}

	@Override
	public QQApi getApi(String accessToken) {
		return new QQApiImpl(accessToken, appId);
	}

}
