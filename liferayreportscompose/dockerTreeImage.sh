#!/usr/bin/env bash
docker images --viz | dot -Tpng -o docker.png
