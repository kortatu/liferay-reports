#!/usr/bin/env bash
docker run --link jaskula-mysql:mysql --link reports_rest_service:rest-service -d --name jaskula com.cgi.ecm.reports:jaskula-0.0.1-SNAPSHOT
