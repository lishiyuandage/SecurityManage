package com.bigbrotherlee.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.bigbrotherlee.advice.MyInterceptor;
import com.bigbrotherlee.advice.TimeFilter;

@Configuration
public class AdviceConfig  extends WebMvcConfigurerAdapter{
	
		@Autowired
		private MyInterceptor myInterceptor;
		@Bean
		public FilterRegistrationBean adviceFilter() {
			FilterRegistrationBean bean=new FilterRegistrationBean();//filter描述类bean			
			bean.addUrlPatterns("/*");//你要拦截的url
			bean.setFilter(new TimeFilter());
			return bean;
		}
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(myInterceptor);
			super.addInterceptors(registry);
		}
		
		
}
