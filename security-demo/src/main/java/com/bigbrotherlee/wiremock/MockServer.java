package com.bigbrotherlee.wiremock;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.removeAllMappings;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;



public class MockServer {

	public static void main(String[] args) {
		configureFor(8081);
		removeAllMappings();
		
		try {
			mock("/order/1","01");
			mock("/order/2","02");
		} catch (Exception e) {}
	}
	private static void mock(String url,String filename) throws Exception{
		ClassPathResource resource=new ClassPathResource("mock/response/"+filename+".json");
		String responseString=FileUtils.readFileToString(resource.getFile(),Charset.defaultCharset());
		stubFor(get(urlEqualTo(url)).willReturn(
						aResponse().withBody(responseString).withStatus(200)
				));//模拟一个服务
	}
	

}
