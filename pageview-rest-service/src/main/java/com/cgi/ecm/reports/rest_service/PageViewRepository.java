package com.cgi.ecm.reports.rest_service;

import java.util.Date;
import java.util.List;


import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

public interface PageViewRepository extends MongoRepository<PageView, Long> {

    List<PageView> findByViewerSession(@Param("session") String session);
    List<PageView> findByViewTimestampGreaterThan(@Param("from") Date from);
}
