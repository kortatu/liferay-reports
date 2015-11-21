#!/usr/bin/env bash
docker run --name jaskula-mysql -e MYSQL_ROOT_PASSWORD=jaskula -e MYSQL_DATABASE=jaskula -e MYSQL_USER=jaskula -e MYSQL_PASSWORD=jaskula -P -d mysql
