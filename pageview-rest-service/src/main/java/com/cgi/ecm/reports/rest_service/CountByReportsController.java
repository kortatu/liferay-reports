package com.cgi.ecm.reports.rest_service;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.MapReduceResults;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class CountByReportsController {

    private static final String REDUCE_COUNT = "reduceCount";
    private static final String SLASH = "/";
    private static final String CLASSPATH_QUERIES = "classpath:queries";
    private static final String JS_EXT = ".js";
    private final MongoTemplate mongoTemplate;

    @Autowired
    public CountByReportsController(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    @RequestMapping(method = GET, value = "/reports/mostViewedPortlets")
    @Description("Count most viewed portlets. By default is grouped by portlet type (58_INSTANCE_77676 and 58_INSTANCE_hayas are taken as 58. " +
            " In order to view by portlet instance use parameter reportType='mapMostVisitedPortlets'.")
    public List<GroupCount> getMostViewedPortlets(@RequestParam(defaultValue = "PORTLET_TYPE") MostViewedPortletType reportType) {
        return groupByMapFunction(reportType.mapJavascriptName);
    }

    @RequestMapping(method = GET, value = "/reports/longestSessions")
    public List<GroupCount> getLongestSessions() {
        return groupByMapFunction("longestSessions");
    }

    @RequestMapping(method = GET, value = "/reports/referer")
    public List<GroupCount> getByReferer() {
        return groupByMapFunction("referer");
    }


    public enum MostViewedPortletType {
        INSTANCES("mapMostVisitedPortlets"), PORTLET_TYPE("mapMostVisitedPortletTypes");
        private final String mapJavascriptName;

        MostViewedPortletType(String mapJavascriptName) {
            this.mapJavascriptName = mapJavascriptName;
        }
    }

    private List<GroupCount> groupByMapFunction(String mapFunction) {
        MapReduceResults<ValueObject> mostVisitedPortlets = mongoTemplate.mapReduce("pageView",
                MessageFormat.format("{0}{1}{2}{3}", CLASSPATH_QUERIES, SLASH, mapFunction, JS_EXT),
                MessageFormat.format("{0}{1}{2}{3}", CLASSPATH_QUERIES, SLASH, REDUCE_COUNT, JS_EXT),
                ValueObject.class);
        List<GroupCount> result = new ArrayList<>();
        for (ValueObject valueObject : mostVisitedPortlets) {
            result.add(new GroupCount(valueObject.getId(), valueObject.getValue()));
        }
        Collections.sort(result);
        return result;
    }

    private static class ValueObject {
        private String id;
        private long value;

        private String getId() {
            return id;
        }

        private long getValue() {
            return value;
        }
    }

    public class GroupCount implements Comparable<GroupCount> {

        private final String id;
        private final long count;

        public GroupCount(String id, long count) {
            super();
            this.id = id;
            this.count = count;
        }

        public String getId() {
            return id;
        }

        public long getCount() {
            return count;
        }

        /**
         * Sorting is inverted because we always want highest count first
         */
        @Override
        public int compareTo(@NotNull GroupCount o) {
            return Long.compare(count, o.count) * -1;
        }

    }

}
