package com.cgi.ecm.reports;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import org.springframework.web.context.request.RequestContextListener;

public class ReportAgentPreAction extends Action {

	static final String COM_CGI_ECM_REPORTS_START_TIME = "com.cgi.ecm.reports.startTime";
    private final RequestContextFilter requestContextFilter = new RequestContextFilter();

    @Override
	public void run(HttpServletRequest req, HttpServletResponse res)
			throws ActionException {
		req.getSession().setAttribute(COM_CGI_ECM_REPORTS_START_TIME, System.currentTimeMillis());
        requestContextFilter.setRequestContext(req);
	}


}
