package com.bigbrotherlee.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author lee
 * 验证码生成接口
 */
public interface ValidateCodeGenerator {
	
	/**
	 * 验证码生成器，调用这个方法生成验证码对象
	 * @param request 用来取得自定义参数，如：图片宽高
	 * @return 返回验证码对象，对象内有过期时间和验证码
	 */
	ValidateCode generate(ServletWebRequest request);
}
