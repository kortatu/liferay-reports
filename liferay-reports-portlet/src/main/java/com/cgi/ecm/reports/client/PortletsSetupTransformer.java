package com.cgi.ecm.reports.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.integration.annotation.Transformer;

import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalService;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

public class PortletsSetupTransformer {
	
	private LayoutLocalService layoutLocalService;
	private PortletPreferencesFactory portletPreferencesFactory;

	@Transformer
	public PageView addPortletSetup(PageView pageView) throws Exception {
		Layout layout = getLayoutLocalServirce().getLayout(pageView.getPlid());
		Collection<PortletSetup> portletSetups = new ArrayList<>();
		for (String portletId : pageView.getPortlets()) {
			Map<String, String[]> map = getPortletPreferencesFactory().getStrictLayoutPortletSetup(layout, portletId).getMap();
			portletSetups.add(new PortletSetup(portletId,map));
		}
		pageView.setPortletSetups(portletSetups);
		return pageView;
	}

	private PortletPreferencesFactory getPortletPreferencesFactory() {
		if (portletPreferencesFactory == null)
			portletPreferencesFactory = PortletPreferencesFactoryUtil.getPortletPreferencesFactory();
		return portletPreferencesFactory;
	}
	
	public void setPortletPreferencesFactory(PortletPreferencesFactory portletPreferencesFactory) {
		this.portletPreferencesFactory = portletPreferencesFactory;
	}
	
	private LayoutLocalService getLayoutLocalServirce() {
		if (layoutLocalService == null)
			layoutLocalService = LayoutLocalServiceUtil.getService();
		return layoutLocalService;
	}
	
	public void setLayoutLocalService(LayoutLocalService layoutLocalService) {
		this.layoutLocalService = layoutLocalService;
	}

}
