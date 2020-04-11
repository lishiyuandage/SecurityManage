package com.bigbrotherlee.security.core.validate.code.sms;

/**
 * 默认的验证码发送器，你应该重写这个类
 * @author lee
 */
public class DefaultSmsCodeSender implements CodeSender {
	
	@Override
	public void send(String phone, String code) {
		System.out.println("发送验证码"+code+"到"+phone);
	}

}
