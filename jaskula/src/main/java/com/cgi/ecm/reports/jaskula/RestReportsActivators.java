package com.cgi.ecm.reports.jaskula;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.messaging.MessageHandler;

/**
 * Configuration class for adding the final activators of JaskulaPipeline.
 * It is separated because they connect externally and for testing purposes it is better
 * this way
 * Created by kortatu on 28/07/15.
 */
@Configuration
public class RestReportsActivators {

    @Bean
    public RestReportsConfig restReportsConfig() {
        return new RestReportsConfig();
    }

    @Bean
    public PageViewerClient pageViewerClient(RestReportsConfig restReportsConfig) {
        return new PageViewerClient(restReportsConfig);
    }

    @Bean(name="joinedServiceActivator")
    @ServiceActivator(inputChannel = "joinedChannel",outputChannel = "logReplyChannel")
    public MessageHandler joinedServiceActivator(PageViewerClient pageViewerClient) {
        return new ServiceActivatingHandler(pageViewerClient, "savePageViews");
    }

    @Bean(name="singlepostServiceActivator")
    @ServiceActivator(inputChannel = "singlepostInputChannel",outputChannel = "logReplyChannel")
    public MessageHandler singlepostServiceActivator(PageViewerClient pageViewerClient) {
        return new ServiceActivatingHandler(pageViewerClient, "savePageView");
    }


}
