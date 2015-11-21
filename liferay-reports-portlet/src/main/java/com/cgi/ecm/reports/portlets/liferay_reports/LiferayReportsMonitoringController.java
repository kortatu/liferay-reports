package com.cgi.ecm.reports.portlets.liferay_reports;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

import com.cgi.ecm.reports.EcmReportsAgent;
import com.cgi.ecm.reports.PortalPropertiesI;

/**
 * Controller for monitoring the behaviour and performance of LiferayReports
 * Created by kortatu on 30/07/15.
 */
@Controller
@RequestMapping("VIEW")
public class LiferayReportsMonitoringController {

    @Resource
    private EcmReportsAgent ecmReportsAgent;

    @Resource
    private MessageChannel requestChannel;

    @Resource
    private PortalPropertiesI portalProperties;

    @RequestMapping
    public ModelAndView view() {
        Map<String, Object> model = new HashMap<>();
        model.put("enabled", portalProperties.isReportsEnabled());
        model.put("localJaskula",portalProperties.isJaskulaLocal());
        return new ModelAndView("monitoring", model);

    }
    
    @ResourceMapping(value = "clientInfo")
	public void clientInfo(ResourceRequest resourceRequest,
			ResourceResponse resourceResponse) throws IOException,
			PortletException {

		String result = resourceRequest.getParameter("info");
		System.out.println("INFO: "+result);

	}

}
