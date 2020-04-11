package com.bigbrotherlee.validate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MyConstraintValidator implements ConstraintValidator<MyValidate, Object> {

	@Override
	public void initialize(MyValidate arg0) {
		System.out.println("初始化 ---------------------------MyConstraintValidator----------------------------------");
	}
	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		System.out.println("=================="+value+"=======================");
		return false;
	}

}
