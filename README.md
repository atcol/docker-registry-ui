docker-registry-web
===================

A Grails web application for easy Docker Registry integration. Allows you to browse, delete and search for images as
well as register multiple registries for large registry installations.

# Getting Started - Building Yourself

Build the web-app with `grails war docker/docker-registry-web.war`. Now you build the docker image:

	`docker build docker/`

and run it:
	
	`docker run -dt -p 8080:8080 <image id from last command>`

then browse to localhost:8080/docker-registry-web/registry/index and create a new registry (using the host's external
IP).

