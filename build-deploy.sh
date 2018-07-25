#!/bin/bash
./grailsw.sh clean && \
  ./grailsw.sh war docker-registry-ui.war && \
	docker build .

