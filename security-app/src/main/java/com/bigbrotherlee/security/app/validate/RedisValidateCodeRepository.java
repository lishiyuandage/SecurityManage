package com.bigbrotherlee.security.app.validate;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.validate.code.ValidateCode;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeException;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeRepository;
import com.bigbrotherlee.security.core.validate.code.ValidateCodeType;

@Component("validateCodeRepository")
@ConditionalOnMissingBean(name = "validateCodeRepository")
public class RedisValidateCodeRepository implements ValidateCodeRepository {
	@Autowired 
	private RedisTemplate<Object, Object> redisTemplate;
	
	@Override
	public ValidateCode get(ServletWebRequest request, ValidateCodeType type) {
		Object value=redisTemplate.opsForValue().get(buildKey(request, type));
		if(value==null) {
			return null;
		}
		return (ValidateCode)value;
	}

	@Override
	public void save(ServletWebRequest request, ValidateCode code, ValidateCodeType type) {
		redisTemplate.opsForValue().set(buildKey(request,type), code, 30, TimeUnit.MINUTES);
	}


	@Override
	public void remove(ServletWebRequest request, ValidateCodeType type) {
		redisTemplate.delete(buildKey(request, type));
	}
	
	private Object buildKey(ServletWebRequest request, ValidateCodeType type) {
		String deviceId=request.getHeader("deviceId");
		if(StringUtils.isBlank(deviceId)) {
			throw new ValidateCodeException("deviceId 错误");
		}
		return "code:"+type.toString().toUpperCase()+":"+deviceId;
	}
}
