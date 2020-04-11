package com.bigbrotherlee.security.core.validate.code;

import java.io.Serializable;
import java.time.LocalDateTime;

public class ValidateCode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5770897706518632524L;
	
	private String code;//验证码
	private LocalDateTime expireTime;//过期时间
	
	public ValidateCode(String code, int expireIn) {
		super();
		this.code = code;
		this.expireTime = LocalDateTime.now().plusSeconds(expireIn);
	}
	public ValidateCode(String code, LocalDateTime expireTime) {
		super();
		this.code = code;
		this.expireTime = expireTime;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public LocalDateTime getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(LocalDateTime expireTime) {
		this.expireTime = expireTime;
	}
	public boolean isExpire() {
		return LocalDateTime.now().isAfter(expireTime);
	}
}
