package com.bigbrotherlee.security.core.social;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

public class LeeConnectionSuccessView extends AbstractView {
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setContentType("text/html;charset=utf-8");//发送数据
		if(model.get("connection")==null) {
			response.getWriter().write("<h4>解绑成功</h4>");
		}else {
			response.getWriter().write("<h4>绑定成功</h4>");
		}
	}

}
