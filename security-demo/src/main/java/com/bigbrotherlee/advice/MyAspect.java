package com.bigbrotherlee.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
//一般用来全局日志
@Aspect
@Component
public class MyAspect {
	@Around("execution(* com.bigbrotherlee.controller.*.*(..))")
	public Object handleController(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("MyAspect ------------------------------before");
		Object o= joinPoint.proceed();
		System.out.println("MyAspect ------------------------------after");
		return o;
	}
}
