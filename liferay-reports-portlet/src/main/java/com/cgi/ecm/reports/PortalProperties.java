package com.cgi.ecm.reports;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PortalProperties implements PortalPropertiesI {

    public static final String REPORTS_NAMESPACE = "com.cgi.ecm.reports";

    @Autowired(required = false)
    private PortletPreferencesFactory portletPreferencesFactory;

    @Override
    public boolean isReportsEnabled() {
        return getBooleanProperty("sendingEnable", true);
    }

    @Override
    public void setSendingEnabled(boolean enabled) {
        setProperty("sendingEnable", enabled);
    }

    @Override
    public boolean isJaskulaLocal() {
        return getBooleanProperty("jaskulaLocal", true);
    }

    @Override
    public void setJaskulaLocal(boolean local) {
        setProperty("jaskulaLocal", local);
    }

    private boolean getBooleanProperty(String propertyName, boolean defaultValue) {
        try {
            PortalPreferences portalPreferences = getPortalPreferences();
            return GetterUtil.getBoolean(portalPreferences.getValue(REPORTS_NAMESPACE, propertyName), defaultValue);
        } catch (SystemException e) {
            System.out.println("Cannot get portal preferences: " + e);
            return false;
        }
    }

    private void setProperty(String propertyName, Object value) {
        try {
            getPortalPreferences().setValue(REPORTS_NAMESPACE, propertyName, String.valueOf(value));
        } catch (SystemException e) {
            System.out.println("Cannot write enable portal preference: " + e);
        }
    }

    private PortalPreferences getPortalPreferences() throws SystemException {
        return getPortletPreferencesFactory().getPortalPreferences(0, true);
    }

    private PortletPreferencesFactory getPortletPreferencesFactory() {
        if (portletPreferencesFactory == null)
            portletPreferencesFactory = PortletPreferencesFactoryUtil.getPortletPreferencesFactory();
        return portletPreferencesFactory;
    }

    void setPortletPreferencesFactory(PortletPreferencesFactory portalPreferencesFactory) {
        this.portletPreferencesFactory = portalPreferencesFactory;
    }


}
