#!/bin/bash
./grailsw clean && \
  ./grailsw war docker-registry-ui.war && \
	docker build .

