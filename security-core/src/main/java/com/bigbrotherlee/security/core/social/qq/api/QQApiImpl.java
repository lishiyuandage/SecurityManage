package com.bigbrotherlee.security.core.social.qq.api;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;

import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * 使用它
 * @author lee
 *
 */
public class QQApiImpl extends AbstractOAuth2ApiBinding implements QQApi {
	Logger logger=LoggerFactory.getLogger(QQApiImpl.class);
	private String appId;
	
	private String openId;
	
	private ObjectMapper objectMapper=new ObjectMapper();
	
	//得到用户的openid的url
	private static final String GET_USER_OPENID_URL=
				"https://graph.qq.com/oauth2.0/me?access_token=%s";
	//得到用户信息的url
	private static final String GET_USER_INFO_URL=
			"https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
	
	public QQApiImpl(String accessToken,String appId) {
		super(accessToken,TokenStrategy.ACCESS_TOKEN_PARAMETER);
		this.appId=appId;
		String url=String.format(GET_USER_OPENID_URL, accessToken);
		String result=getRestTemplate().getForObject(url,String.class);
		logger.info(result);
		this.openId=StringUtils.substringBetween(result, "\"openid\":\"","\"}");
	}
	
	@Override
	public QQUserInfo getUserInfo() {
		String url=String.format(GET_USER_INFO_URL, appId,openId);
		String result=getRestTemplate().getForObject(url, String.class);
		
		logger.info(result);
		
		QQUserInfo info=null;
		try {
			info =objectMapper.readValue(result, QQUserInfo.class);
			info.setOpenId(openId);
			return info;
		} catch (Exception e) {
			throw new RuntimeException("获取QQ用户消息失败----------"+e.getMessage());
		}
	}

}
