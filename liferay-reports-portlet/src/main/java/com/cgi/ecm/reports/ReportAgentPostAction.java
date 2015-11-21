package com.cgi.ecm.reports;


import com.cgi.ecm.reports.client.PageView;
import com.cgi.ecm.reports.client.PageViewerClientI;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ReportAgentPostAction extends Action {
	

	private PageViewerClientI pageViewerClient;
    private RequestContextFilter requestContextFilter = new RequestContextFilter();

	@Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws ActionException {
		Long start = (Long)req.getSession().getAttribute(ReportAgentPreAction.COM_CGI_ECM_REPORTS_START_TIME);
		long end = System.currentTimeMillis();
		if (start!=null) {
			long total = end-start;
			ThemeDisplay themeDisplay = (ThemeDisplay) req.getAttribute(WebKeys.THEME_DISPLAY);
			pageViewerClient().savePageView(new PageView("educamadrid", "emportal51", themeDisplay.getCompanyId(), themeDisplay.getUserId(), themeDisplay.getUser().getEmailAddress(), req.getSession().getId(), themeDisplay.getLayout().getPlid(),
					themeDisplay.getLayout().getFriendlyURL(), themeDisplay.getLayoutTypePortlet().getPortletIds(), themeDisplay.getDevice(), total, req.getParameterMap(),getHeaders(req)));
			System.out.println("Total time for reportAgent: ["+(System.currentTimeMillis()-end)+"]");
		}
		requestContextFilter.removeRequestContext(req);
	}

    private Map<String, String> getHeaders(HttpServletRequest req) {
	    Map<String, String> list = new HashMap<String, String>();
	    @SuppressWarnings("unchecked")
	    Enumeration<String> headerNames= req.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
		String header = headerNames.nextElement();
		list.put(header,req.getHeader(header));
	    }
	    return list;
	}

	private PageViewerClientI pageViewerClient() {
		if (pageViewerClient == null) {
			pageViewerClient = EcmReportsAgent.getAgent().getPageViewerClient();
		}
		return pageViewerClient;
	}


}