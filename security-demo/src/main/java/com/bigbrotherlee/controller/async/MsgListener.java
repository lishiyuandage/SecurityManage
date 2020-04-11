package com.bigbrotherlee.controller.async;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class MsgListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private MockMsg msg;
	@Autowired
	private DefferentResultHandler defferentResultHandler;
	private Logger log=LoggerFactory.getLogger(getClass());
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		new Thread(()->{
			while(true) {
				if(StringUtils.isNotBlank(msg.getEnd())) {
					String num=msg.getEnd();
					log.info("监听器-------------------------------------");
					defferentResultHandler.getMap().get(num).setResult("这是处理结果===================");
					msg.setEnd(null);
				}else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
	}

}
