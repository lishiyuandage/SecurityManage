package com.bigbrotherlee.controller.async;

import java.util.concurrent.Callable;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class AsyncController {
	@Autowired
	private MockMsg msg;
	@Autowired
	private DefferentResultHandler defferentResultHandler;
	
	private Logger log=LoggerFactory.getLogger(getClass());
	
	@RequestMapping("/order")
	public String order() throws Exception {
		log.info("start--------------------");
		Thread.sleep(2000);
		log.info("end--------------------");
		return "SUCCESS";
	}
	
	/*
	 * 异步只是后台的异步，前台还是要等
	 */
	//异步处理
	@RequestMapping("/call")
	public Callable<String>  callAsync() throws Exception {
		log.info("start--------------------");
		Callable<String> result=new Callable<String>() {
			public String call() throws Exception {
				log.info("new start--------------------");
				Thread.sleep(2000);
				log.info("new end--------------------");
				return "success";
			}
		};
		log.info("end--------------------");
		return result;
	}
	
	//异步处理,DeferredResult是springweb的一个类，里面的泛型是你要返回的结果。这是消息队列（生产者消费者）模式
	@RequestMapping("/deffer")
	public DeferredResult<String>  defferAsync() throws Exception {
		log.info("start--------------------");
		String orderNumber=RandomStringUtils.randomNumeric(8);
		msg.setStart(orderNumber);
		DeferredResult<String> result=new DeferredResult<>();
		defferentResultHandler.getMap().put(orderNumber, result);		
		log.info("end--------------------");
		return result;
	}
	
}
