package com.cgi.ecm.reports;

import javax.portlet.PortletRequest;

/**
 * Created by kortatu on 30/07/15.
 */
public interface PortalPropertiesI {
    boolean isReportsEnabled();

    void setSendingEnabled(boolean enabled);

    boolean isJaskulaLocal();

    void setJaskulaLocal(boolean local);

}
