#!/bin/bash
./grailsw war docker-registry-web.war && \
	scp docker-registry-web.war $1:$2

