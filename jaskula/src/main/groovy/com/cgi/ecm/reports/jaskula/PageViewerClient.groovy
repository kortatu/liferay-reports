package com.cgi.ecm.reports.jaskula

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.apache.http.conn.HttpHostConnectException
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.messaging.Message;

import groovyx.net.http.HttpResponseDecorator;
import groovyx.net.http.RESTClient
import groovyx.net.http.ContentType;

@MessageEndpoint("jsonPageViewerClient")
class PageViewerClient {

	private static final Log log = LogFactory.getLog(PageViewerClient.class);
	private static final String HOST = "http://localhost:8081"
	private static final String PAGE_VIEW_PATH = "/pageViews"
	private static final String MULTI_POST_PAGE_VIEW_PATH = "/multiPosts/pageViews"
	RESTClient pageViewWebRest; 

    @Autowired
	public PageViewerClient(RestReportsConfig config) {
        setRestReportsConfig(config);
	}
	
	private RESTClient initpageViewWebRest(String host) {
		try {
			log.info("Initializing RESTClient(${this}) with host: "+host)
			this.pageViewWebRest = new RESTClient(host);
			pageViewWebRest.parser.'application/hal+json' = pageViewWebRest.parser.'application/json'
			return pageViewWebRest
		} catch (Exception e) {
			log.error("Host not configured: "+e);
			e.printStackTrace()
		}
		
	}
	
	public void setRestReportsConfig(RestReportsConfig config) {
		if (config.host)
			initpageViewWebRest(config.host);
		else
		    initpageViewWebRest(HOST);
	}
	
//	@ServiceActivator
	public void savePageView(Object pageView) {
		this.savePageViewAndGet(pageView);
	}
	
//	@ServiceActivator
	public void savePageViews(Message<?> pageViewMessage) {
		def pageViews = pageViewMessage.payload;
		log.info("PageViews aggregated size: ${pageViews.size()}")
		try {
			def resp = pageViewWebRest.post(
                path: "${MULTI_POST_PAGE_VIEW_PATH}",
                body :  pageViews.collect { pageViewJson(it) },
                requestContentType : ContentType.JSON)
			if (resp.status == 201) {
                log.info "Batch posted Ok"
            } else {
                log.info "Response code $resp.status"
            }
		} catch (HttpHostConnectException ignored) {
			log.error("Error connecting to RestReportService. Check configuration and availability. Current endpoint is ${pageViewWebRest.uri}")
		}
	}
	
	private String savePageViewAndGet(Object pageView) {
		def resp = pageViewWebRest.post(path: "${PAGE_VIEW_PATH}",
			body : pageViewJson(pageView),
			requestContentType : ContentType.JSON)
		if (resp.status == 201) {
			resp.headers.Location
		} else {
			log.info "Response code $resp.status"
			return null;
		}
	}
	
	def pageViewJson = {pageView ->
		[ applicationId: pageView.applicationId,
			nodeId: pageView.nodeId,
			companyId: pageView.companyId,
			viewer: [ 
				userId: pageView.userId,
				userEmail: pageView.userEmail,
				session: pageView.session
				],
			page: [
				pageId: pageView.plid,
				pageName: pageView.pageName,
				portlets: pageView.portletSetups
				],
			viewTimestamp: pageView.timestamp,
			device: pageView.device,
			parameters: pageView.parameters.collect {k,v -> [key:k, value: v]},
			processTime: pageView.elapsedTime,
			headers: pageView.headers
		] 
	}
	
	
	public void getPageView(String url) {
		def resp = pageViewWebRest.get(
			path:url, requestContentType: ContentType.JSON) { HttpResponseDecorator resp, json ->
			if (resp.status == 200) {
				log.debug resp.contentType
				log.debug json.applicationId
			}
		}
	}
}
