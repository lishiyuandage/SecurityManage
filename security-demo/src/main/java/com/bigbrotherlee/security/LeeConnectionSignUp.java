package com.bigbrotherlee.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.stereotype.Component;

@Component("connectionSignUp")
public class LeeConnectionSignUp implements ConnectionSignUp{
	//社交账号静默注册
	@Override
	public String execute(Connection<?> connection) {
		//根据社交信息写注册逻辑
		
		return connection.getDisplayName();
	}

}
