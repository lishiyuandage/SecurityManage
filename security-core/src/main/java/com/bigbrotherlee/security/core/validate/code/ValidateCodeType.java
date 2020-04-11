package com.bigbrotherlee.security.core.validate.code;

import com.bigbrotherlee.security.core.properties.SecurityConstants;

/**
 * 验证码类型
 * @author lee
 *
 */
public enum ValidateCodeType {
	
	/**
	 * 图片验证码
	 * @author lee
	 *
	 */
	IMAGE{
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_IMAGE;
		}
		
	},
	
	/**
	 * 短信验证码
	 * @author lee
	 */
	SMS {
		@Override
		public String getParamNameOnValidate() {
			return SecurityConstants.DEFAULT_PARAMETER_NAME_CODE_SMS;
		}
	};
	/**
	 * 校验时从请求中获取的参数的名字
	 * @return
	 */
	public abstract String getParamNameOnValidate();
}
