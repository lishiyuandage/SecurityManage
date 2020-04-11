package com.bigbrotherlee.security.core.social;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.AbstractView;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component("/connect/status")
public class LeeConnectionStatusView extends AbstractView {
	
	@Autowired
	private ObjectMapper objectMapper;
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String,List<Connection<?>>> map=(Map<String, List<Connection<?>>>) model.get("connectionMap");//得到社交记录
		
		Map<String,Boolean> result=new HashMap<String, Boolean>();//处理数据
		for(String key:map.keySet()) {
			result.put(key, CollectionUtils.isNotEmpty(map.get(key)));
		}
		
		response.setContentType("application/json;charset=utf-8");//发送数据
		response.getWriter().write(objectMapper.writeValueAsString(result));
	}

}
