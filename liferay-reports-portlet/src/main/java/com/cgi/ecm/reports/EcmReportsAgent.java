package com.cgi.ecm.reports;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.cgi.ecm.reports.client.PageViewerClientI;

@Component
public class EcmReportsAgent implements ApplicationContextAware {

	private ApplicationContext appContext;
	private static EcmReportsAgent ecmReportAgent;
	
	@Autowired
	private PageViewerClientI pageViewerClient;

	@Override
	public void setApplicationContext(ApplicationContext appContext)
			throws BeansException {
		this.appContext = appContext;
		EcmReportsAgent.ecmReportAgent = this;
	}
	
	public static EcmReportsAgent getAgent() {
		return EcmReportsAgent.ecmReportAgent; 
	}

	public PageViewerClientI getPageViewerClient() {
		return pageViewerClient;
	}
	

}
