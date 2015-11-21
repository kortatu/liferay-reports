package com.cgi.ecm.reports.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by kortatu on 30/07/15.
 */
public class PortletSetup implements Serializable {

    private String portletId;
    private Map<String, String[]> portletSetup;

    public PortletSetup(String portletId, Map<String, String[]> portletSetup) {
        this.portletId = portletId;
        this.portletSetup = portletSetup;
    }

    public String getPortletId() {
        return portletId;
    }

    public Map<String, String[]> getPortletSetup() {
        return portletSetup;
    }
}
