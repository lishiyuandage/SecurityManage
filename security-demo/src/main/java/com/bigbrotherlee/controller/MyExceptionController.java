package com.bigbrotherlee.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 自定义全局异常
 * @author li
 * @createdate 2019年4月20日
 */
@ControllerAdvice
public class MyExceptionController {
	
	@ExceptionHandler(MyException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public Map<String, Object> handleMyException(MyException exception) {
		Map<String, Object> map=new HashMap<>();
		map.put("id", "-----------------"+exception.getId());
		map.put("msg", exception.getMessage());
		return map;
	}
}
