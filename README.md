[![Build Status](https://travis-ci.org/atc-/docker-registry-web.svg?branch=master)](https://travis-ci.org/atc-/docker-registry-web)

docker-registry-web
===================

A Grails web application for easy private/local Docker Registry integration. Allows you to browse, delete and search for images as
well as register multiple registries for large installations.

# Demo

This project is containerized. You can run with docker right now by simply running:

	docker run -p 8080:8080 atcol/docker-registry-web

and browsing to localhost:8080/

# Building

Build the web-app with `grails war docker/docker-registry-web.war`. Now you build the docker image:

	docker build docker/

and run it:
	
	docker run -dt -p 8080:8080 <image id from last command>

then browse to localhost:8080/docker-registry-web/registry/index and create a new registry (using the host's external
IP).
