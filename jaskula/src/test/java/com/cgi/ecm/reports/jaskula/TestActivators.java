package com.cgi.ecm.reports.jaskula;

import com.cgi.ecm.reports.jaskula.test.PageViewerTestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.ServiceActivatingHandler;
import org.springframework.messaging.MessageHandler;

/**
 * Created by kortatu on 28/07/15.
 */
@Configuration
public class TestActivators {

    @Bean
    public PageViewerTestClient jsonPageViewerClient() {
        return new PageViewerTestClient();
    }

    @Bean(name="joinedServiceActivator")
    @ServiceActivator(inputChannel = "joinedChannel",outputChannel = "logReplyChannel")
    public MessageHandler joinedServiceActivator(PageViewerTestClient pageViewerTestClient) {
        return new ServiceActivatingHandler(pageViewerTestClient, "savePageViews");
    }

//    @Bean(name="singlepostServiceActivator")
//    @ServiceActivator(inputChannel = "singlepostInputChannel",outputChannel = "logReplyChannel")
//    public MessageHandler singlepostServiceActivator(PageViewerTestClient pageViewerTestClient) {
//        return new ServiceActivatingHandler(pageViewerTestClient, "savePageView");
//    }


}
