package com.cgi.ecm.reports.client;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.liferay.portal.kernel.mobile.device.Device;

public class PageView implements Serializable {
	private String applicationId;
	private String nodeId;
	private long userId;
	private String userEmail;
	private String session;
	private long plid;
	private String pageName;
	private Collection<String> portlets;
	private Device device;
	private long elapsedTime;
	private Map parameters;
	private long companyId;
	private Date timestamp;
	private Map<String, String> headers;
	private Collection<PortletSetup> portletSetups;

	public PageView(String applicationId, String nodeId, long userId,
			long companyId, String userEmail, String session, long plid,
			String pageName, Collection<String> portlets, Device device,
			long elapsedTime, Map parameters, Map<String, String> headers) {
		this.applicationId = applicationId;
		this.nodeId = nodeId;
		this.companyId = companyId;
		this.userId = userId;
		this.userEmail = userEmail;
		this.session = session;
		this.plid = plid;
		this.pageName = pageName;
		this.portlets = portlets;
		this.device = device;
		this.elapsedTime = elapsedTime;
		this.parameters = parameters;
		this.headers = headers;
		this.timestamp = new Date();
	}

	public String getApplicationId() {
		return applicationId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public long getUserId() {
		return userId;
	}

	public String getUserEmail() {
		return userEmail;
	}
	
	public String getSession() {
		return session;
	}

	public long getPlid() {
		return plid;
	}

	public String getPageName() {
		return pageName;
	}

	public Collection<String> getPortlets() {
		return portlets;
	}

	public Device getDevice() {
		return device;
	}

	public long getElapsedTime() {
		return elapsedTime;
	}

	public Map getParameters() {
		return parameters;
	}

	public long getCompanyId() {
		return companyId;
	}
	
	public Map<String, String> getHeaders() {
	    return headers;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setPortletSetups(Collection<PortletSetup> portletSetups) {
		this.portletSetups = portletSetups;
	}

	public Collection<PortletSetup> getPortletSetups() {
		return portletSetups;
	}
	
}