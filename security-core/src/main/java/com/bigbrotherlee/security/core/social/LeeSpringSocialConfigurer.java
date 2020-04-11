package com.bigbrotherlee.security.core.social;

import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SpringSocialConfigurer;

public class LeeSpringSocialConfigurer extends SpringSocialConfigurer {
	private String filterProcessesUrl;
	
	public String getFilterProcessesUrl() {
		return filterProcessesUrl;
	}
	public LeeSpringSocialConfigurer(String filterProcessesUrl) {
		this.filterProcessesUrl=filterProcessesUrl;
	}
	

	@Override
	protected <T> T postProcess(T object) {
		SocialAuthenticationFilter filter=(SocialAuthenticationFilter) super.postProcess(object);
		filter.setFilterProcessesUrl(filterProcessesUrl);
		return super.postProcess(object);
	}
}
