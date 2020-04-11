package com.bigbrotherlee.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.METHOD,ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy= MyConstraintValidator.class)
public @interface MyValidate {
	String message() default "MyValidate默认消息";

	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };

}
