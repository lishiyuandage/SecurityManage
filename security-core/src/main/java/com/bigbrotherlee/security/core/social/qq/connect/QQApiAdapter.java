package com.bigbrotherlee.security.core.social.qq.connect;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;

import com.bigbrotherlee.security.core.social.qq.api.QQApi;
import com.bigbrotherlee.security.core.social.qq.api.QQUserInfo;

public class QQApiAdapter implements ApiAdapter<QQApi> {

	@Override
	public boolean test(QQApi api) {
		return true;
	}

	@Override
	public void setConnectionValues(QQApi api, ConnectionValues values) {
		QQUserInfo userInfo=api.getUserInfo();
		values.setDisplayName(userInfo.getNickname());
		values.setImageUrl(userInfo.getFigureurl_qq_1());
		values.setProfileUrl("");
		values.setProviderUserId(userInfo.getOpenId());
	}

	@Override
	public UserProfile fetchUserProfile(QQApi api) {
		return null;
	}

	@Override
	public void updateStatus(QQApi api, String message) {}//qq中无用
	
}
