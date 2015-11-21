package com.cgi.ecm.reports.rest_service;

import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;

import static com.cgi.ecm.spring_utils.EcmLinkBuilder.buildLink;

class PageViewsResourceProcessor implements ResourceProcessor<PagedResources<Resource<PageView>>> {

    @Override
    public PagedResources<Resource<PageView>> process(PagedResources<Resource<PageView>> resource) {
        try {
            resource.add(
        	    buildLink(CountByReportsController.class, "getMostViewedPortlets", "mostViewedPortlets", "reportType"));
            resource.add(
        	    buildLink(CountByReportsController.class, "getLongestSessions", "longestSessions"));
            resource.add(
        	    buildLink(CountByReportsController.class, "getByReferer", "byReferer"));
        } catch (NoSuchMethodException e) {
           throw new RuntimeException("Method "+e.getMessage()+" not found");
        }
        return resource;
    }
}