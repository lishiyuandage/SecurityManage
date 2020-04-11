package com.bigbrotherlee.security.core.validate.code;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;


/**
 * 模板类，生成存储发送验证码都在这里规定好了
 * @author lee
 * @param <T>
 */
public abstract class AbstractValidateCodeProcessor<T extends ValidateCode> implements ValidateCodeProcessor {


	/**
	 * 收集系统中所有{@link ValidateCodeGenerator} 接口的实现, 依赖搜索
	 */
	@Autowired
	private Map<String, ValidateCodeGenerator> validateCodeGenerators;
	@Autowired
	private ValidateCodeRepository validateCodeRepository;
	
	@Override
	public void create(ServletWebRequest request) throws Exception {
		T validateCode = generate(request);//生成
		save(request, validateCode);//保持
		System.out.println("===================="+validateCode.getCode());
		send(request, validateCode);//发送
	}

	/**
	 * 生成验证码
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public T generate(ServletWebRequest request) throws Exception {
		String type = getValidateCodeType(request).toString().toLowerCase();
		String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
		ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
		if (validateCodeGenerator == null) {
			throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
		}
		return (T) validateCodeGenerator.generate(request);
	}

	/**
	 * 保存验证码
	 * @param request
	 * @throws Exception
	 */
	private void save(ServletWebRequest request, ValidateCode validateCode) throws Exception {
		ValidateCode code=new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
		validateCodeRepository.save(request, code, getValidateCodeType(request));
	}

	/**
	 * 发送验证码
	 * @param request
	 * @throws Exception
	 */
	protected abstract void send(ServletWebRequest request, T validateCode) throws Exception;

	/**
	 * 获取验证码类型
	 * @return
	 */
	public ValidateCodeType getValidateCodeType(ServletWebRequest request) {
		String type = StringUtils.substringBefore(getClass().getSimpleName(), "ValidateCodeProcessor");
		return ValidateCodeType.valueOf(type.toUpperCase());
	};
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void validate(ServletWebRequest request) {
		ValidateCodeType processorType = getValidateCodeType(request);
		T codeInSession = (T) validateCodeRepository.get(request, processorType);
		String codeInRequest;
		
		try {
			codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(),
					processorType.getParamNameOnValidate()+"Code");
		} catch (ServletRequestBindingException e) {
			throw new ValidateCodeException("获取验证码的值失败");
		}

		if (StringUtils.isBlank(codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码的值不能为空");
		}

		if (codeInSession == null) {
			throw new ValidateCodeException(processorType + "验证码不存在");
		}

		if (codeInSession.isExpire()) {
			validateCodeRepository.remove(request, processorType);
			throw new ValidateCodeException(processorType + "验证码已过期");
		}

		if (!StringUtils.equals(codeInSession.getCode(), codeInRequest)) {
			throw new ValidateCodeException(processorType + "验证码不匹配");
		}
		validateCodeRepository.remove(request, processorType);
	}

}