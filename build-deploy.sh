#!/bin/bash
./grailsw war docker-registry-ui.war && \
	scp docker-registry-ui.war $1:$2

