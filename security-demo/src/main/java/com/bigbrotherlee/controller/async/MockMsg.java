package com.bigbrotherlee.controller.async;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MockMsg {
	private String start;
	private String end;
	private Logger log=LoggerFactory.getLogger(getClass());
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		new Thread(()->{
			log.info("------------------下单--------------------------");
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.end = start;
			log.info("------------------下单结束--------------------------");
		}).start();
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	

}
