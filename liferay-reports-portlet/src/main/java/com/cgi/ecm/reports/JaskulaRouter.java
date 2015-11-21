package com.cgi.ecm.reports;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

public class JaskulaRouter {

	private Log log = LogFactory.getLog(JaskulaRouter.class);
	private PortalPropertiesI portalProperties;

	@Autowired
	public JaskulaRouter(PortalPropertiesI portalProperties) {
		this.portalProperties = portalProperties;
	}

	@Router
	public String route(Message<?> message) {
		if (portalProperties.isReportsEnabled()) {
			if (portalProperties.isJaskulaLocal()) {
				log.debug("Reports is enabled. Sending it to multipostInputChannel");
				return "multipostInputChannel";
			} else {
				log.debug("Reports is enabled but Jaskula is configured externally. Sending it to externalOutputChannels");
				return "externalOutputChannel";
			}
		} else {
			log.info("Report is not enabled. Not sending to Jaskula");
			return "nullChannel";
		}
	}

}
