package com.cgi.ecm.reports.rest_service.multipost;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Splitter;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cgi.ecm.reports.rest_service.PageView;
import com.cgi.ecm.reports.rest_service.PageViewRepository;

@RestController
public class MultiPostController {

    private static final Log log = LogFactory.getLog(MultiPostController.class);

    @Autowired
    private MultiPostSplitService multiPostSplitService;
    @Autowired
    private PageViewRepository pageViewRepository;

    // tag::pipeline[]
    @RequestMapping(method = POST, value = "/multiPosts/pageViews")
    @ResponseStatus(HttpStatus.CREATED)
    public long multiPost(@RequestBody List<PageView> pageViews) {
        log.info("Processing multipost of size " + pageViews.size());
        multiPostSplitService.splitMultiPost(pageViews);
        return pageViews.size();
    }

    @Splitter(inputChannel = "multiPostChannel", outputChannel = "singlePostChannel")
    public List<PageView> split(List<PageView> pageViews) {
        log.debug("Split pageView list(" + pageViews.size() + ") in several messages");
        return pageViews;
    }

    @ServiceActivator(inputChannel = "singlePostChannel")
    public Date postSinglePageView(PageView pageView) {
        PageView saved = pageViewRepository.save(pageView);
        log.info("Saved ok pageView " + saved.getViewTimestamp());
        return saved.getViewTimestamp();
    }
    // end::pipeline[]
}
