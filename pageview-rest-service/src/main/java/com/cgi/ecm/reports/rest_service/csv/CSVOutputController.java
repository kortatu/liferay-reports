package com.cgi.ecm.reports.rest_service.csv;

import com.cgi.ecm.reports.rest_service.PageView;
import com.cgi.ecm.reports.rest_service.PageViewRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.cgi.ecm.reports.rest_service.csv.DateUtils.addDays;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * Created by kortatu on 11/08/15.
 */
@RestController
public class CSVOutputController {
    private static final Log log = LogFactory.getLog(CSVOutputController.class);

    private final PageViewRepository pageViewRepository;

    @Autowired
    public CSVOutputController(PageViewRepository pageViewRepository) {
        this.pageViewRepository = pageViewRepository;
    }

    @RequestMapping(method = GET, value = "/reports/csvLines" , produces = {"text/csv"})
    public List<PortletReportLine> getCSVReportLines() {
        List<PortletReportLine> lines = new ArrayList<>();
        List<PageView> latestPageViews = new ArrayList<>();
        int daysBack = 0;
        while (lines.size()==0 || daysBack > 2000) {
            daysBack = daysBack + 7 ;
            log.debug("Looking " + daysBack + " days in the past");
            latestPageViews = pageViewRepository.findByViewTimestampGreaterThan(addDays(-daysBack));
            for (PageView pageView : latestPageViews) {
                Collection<PortletReportLine> portletReportLines = pageView.getPortletReportLines();
                lines.addAll(portletReportLines);
            }
        }
        log.info("Pageviews found: " + latestPageViews.size());
        log.info("Total CSV lines generated from pageViews: "+lines.size());
        return lines;
    }


}
