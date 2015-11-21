package com.cgi.ecm.reports.jaskula;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


public class RestReportsConfig {

	private String host;

	@Value("${ecm.reports.restService.host}")
	public void setHost(String host) {
		this.host = host;
	}

	public String getHost() {
		return host;
	}
	
}
