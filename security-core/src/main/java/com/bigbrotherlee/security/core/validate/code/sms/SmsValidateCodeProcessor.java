package com.bigbrotherlee.security.core.validate.code.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.bigbrotherlee.security.core.validate.code.AbstractValidateCodeProcessor;
import com.bigbrotherlee.security.core.validate.code.ValidateCode;

@Component("smsValidateCodeProcessor")
public class SmsValidateCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {
	@Autowired
	private CodeSender smsCodeSender;
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		String phone=ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "phone");
		String code=validateCode.getCode();
		smsCodeSender.send(phone, code);	
	}
}
