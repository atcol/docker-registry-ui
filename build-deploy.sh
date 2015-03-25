#!/bin/bash
./grailsw war docker-registry-ui.war && \
	docker build .

