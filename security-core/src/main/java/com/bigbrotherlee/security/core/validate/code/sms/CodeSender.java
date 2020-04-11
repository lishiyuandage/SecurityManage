package com.bigbrotherlee.security.core.validate.code.sms;

/**
 * 验证码发送器
 * @author lee
 *
 */
public interface CodeSender {
	
	/**
	 * @param phone 你要发送的地址
	 * @param code 你要发送的验证码
	 */
	void send(String phone,String code);
}
