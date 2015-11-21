#!/usr/bin/env bash
docker run --link reports-mongo:mongo --rm -p 8081:8081 --name pageView_rest_service com.cgi.ecm/pageview-rest-service
