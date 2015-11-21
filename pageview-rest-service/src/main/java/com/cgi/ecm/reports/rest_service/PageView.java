package com.cgi.ecm.reports.rest_service;


import java.util.*;

import com.cgi.ecm.reports.rest_service.csv.PortletReportLine;
import org.springframework.data.annotation.Id;



public class PageView {
	private static final Collection<PortletReportLine> EMPTY = new ArrayList<>();
	@Id
	private String id;
	private String applicationId;
	private String nodeId;
	private Long companyId;
	private Viewer viewer;
	private Page page;
	private Date viewTimestamp;
	private Device device;
	private Long processTime;
	private List<Parameter> parameters = new ArrayList<>(10);
	private Map<String,String> headers;
	private Object advanced;
	
	public PageView(String id, String applicationId, String nodeId, Long companyId, Viewer viewer, Page page, List<Parameter> parameters, Object advanced) {
	    super();
	    this.id = id;
	    this.applicationId = applicationId;
	    this.nodeId = nodeId;
	    this.companyId = companyId;
	    this.viewer = viewer;
	    this.page = page;
	    this.parameters = parameters;
		this.advanced = advanced;
	}

	public Collection<PortletReportLine> getPortletReportLines() {
        System.out.println("Portlets = " + page.getPortlets());
        if (page!=null && page.getPortlets()!=null) {
			Collection<PortletReportLine> portletReportLines = new ArrayList<>(page.getPortlets().size());
			System.out.println("Portlets in this page " + page.getPortlets().size());
			for (Portlet portlet : page.getPortlets()) {
				PortletReportLine prl = new PortletReportLine(this, portlet);
				portletReportLines.add(prl);
			}
			return portletReportLines;
		} else
			return EMPTY;
	}

	public String getId() {
	    return id;
	}
	public String getApplicationId() {
	    return applicationId;
	}
	public String getNodeId() {
	    return nodeId;
	}
	public Long getCompanyId() {
	    return companyId;
	}
	public Viewer getViewer() {
	    return viewer;
	}
	public Page getPage() {
	    return page;
	}
	public Object getAdvanced() {
	    return advanced;
	}
	public Date getViewTimestamp() {
	    return viewTimestamp;
	}
	public Device getDevice() {
	    return device;
	}
	public Long getProcessTime() {
	    return processTime;
	}

	public List<Parameter> getParameters() {
	    return parameters;
	}

	public Map<String,String> getHeaders() {
	    return headers;
	}

	@Override
	public String toString() {
	    return "PageView [id=" + id + ", applicationId=" + applicationId + ", nodeId=" + nodeId + ", companyId=" + companyId
		    + ", viewer=" + viewer + ", page=" + page + "]";
	}

	public PageView() {

	}
}
