package com.cgi.ecm.reports.jaskula.test;

import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kortatu on 28/07/15.
 */
@MessageEndpoint("jsonPageViewerTestClient")
public class PageViewerTestClient {

    private List<Object> messagePayloads = new ArrayList<>(5);

    @ServiceActivator
    public void savePageViews(Message<?> pageViewMessage) {
        messagePayloads.add(pageViewMessage.getPayload());
    }

//    @ServiceActivator
//    public void savePageView(Message<?> pageViewMessage) {
//        messagePayloads.add(pageViewMessage.getPayload());
//    }

    public List<Object> getMessagePayloads() {
        return messagePayloads;
    }

    public void clean() {
        messagePayloads = new ArrayList<>(5);
    }

}
