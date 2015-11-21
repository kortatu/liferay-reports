package com.cgi.ecm.reports.rest_service.multipost;

import java.util.List;

import org.springframework.integration.annotation.MessagingGateway;

import com.cgi.ecm.reports.rest_service.PageView;

@MessagingGateway(name = "splitGateway", defaultRequestChannel = "multiPostChannel")
interface MultiPostSplitService {
    void splitMultiPost(List<PageView> multiPageViewList);
}
