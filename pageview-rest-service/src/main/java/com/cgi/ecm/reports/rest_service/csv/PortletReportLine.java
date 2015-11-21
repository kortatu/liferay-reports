package com.cgi.ecm.reports.rest_service.csv;

import com.cgi.ecm.reports.rest_service.PageView;
import com.cgi.ecm.reports.rest_service.Portlet;

import java.text.SimpleDateFormat;

/**
 * A line of information representing a fragment of a PageView related to a single portlet. It contains un-normalized data consumed by
 * some report systems as QlickView. A single PageView element is folded into several lines one for each
 * portlet included in the view.
 *
 * Created by kortatu on 11/08/15.
 */
public class PortletReportLine {

    private final static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    private final PageView pageView;
    private final Portlet portlet;

    public PortletReportLine(PageView pageView, Portlet portlet) {
        this.pageView = pageView;
        this.portlet = portlet;
    }
//tag::fields[]
    public String getApplicationId() {
        return pageView.getApplicationId();
    }

    public String getNodeId() {
        return pageView.getNodeId();
    }

    public String getViewTimestamp() {
        return timestampFormat.format(pageView.getViewTimestamp());
    }

    public Long getCompanyId() {
        return pageView.getCompanyId();
    }

    public Long getUserId() {
        return pageView.getViewer().getUserId();
    }
//end::fields[]
    public String getUserEmail() {
        return pageView.getViewer().getUserEmail();
    }

    public String getSession() {
        return pageView.getViewer().getSession();
    }

    public Long getPageId() {
        return pageView.getPage().getPageId();
    }

    public String getPageName() {
        return pageView.getPage().getPageName();
    }

    public Long getProcessTime() {
        return pageView.getProcessTime();
    }
//tag::fields[]
    public String getPortletId() {
        return portlet.getPortletId();
    }

    public String getPortletSetup() {
        return portlet.getPortletSetup()!=null?portlet.getPortletSetup().toString():"";
    }

    public String getPortletAdditionalConfig() {
        return portlet.getAdditionalConfig()!=null?portlet.getAdditionalConfig().toString(): "";
    }
//end::fields[]
    //TODO: Unfold Device
//    public Device getDevice() {
//        return pageView.getDevice();
//    }
//
//    public Map<String, String> getHeaders() {
//        return pageView.getHeaders();
//    }
//
//    public List<Parameter> getParameters() {
//        return pageView.getParameters();
//    }
}