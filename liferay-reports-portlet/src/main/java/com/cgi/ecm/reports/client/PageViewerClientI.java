package com.cgi.ecm.reports.client;

import java.util.List;

public interface PageViewerClientI {

	public abstract void savePageView(PageView pageView);
	
	public abstract void savePageViews(List<PageView> pageViews);

}
