package com.cgi.ecm.reports.portlets.liferay_reports;

import com.cgi.ecm.reports.EcmReportsAgent;
import com.cgi.ecm.reports.PortalPropertiesI;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.ActionMapping;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for configuring several aspects of LiferayReports.
 * Created by kortatu on 30/07/15.
 */
@Controller
@RequestMapping("EDIT")
public class LiferayReportsConfigurationController {

    @Resource
    private EcmReportsAgent ecmReportsAgent;

    @Resource
    private MessageChannel requestChannel;

    @Resource
    private PortalPropertiesI portalProperties;

    @RequestMapping
    public ModelAndView edit() {
        Map<String, Object> model = new HashMap<>();
        model.put("enabled",portalProperties.isReportsEnabled());
        model.put("localJaskula",portalProperties.isJaskulaLocal());
        return new ModelAndView("configuring", model);

    }

    @ActionMapping(params = "action=switchJaskula")
    public void switchLocalJaskula() {
        boolean switchedValue = !portalProperties.isJaskulaLocal();
        System.out.println("Switching Jaskula local to " + switchedValue);
        portalProperties.setJaskulaLocal(switchedValue);
    }

    @ActionMapping(params = "action=switchReports")
    public void switchEnableJaskula() {
        boolean switchedValue = !portalProperties.isReportsEnabled();
        System.out.println("Switching Reports Enabled to "+switchedValue);
        portalProperties.setSendingEnabled(switchedValue);
    }
}
