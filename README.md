[![Build Status](https://travis-ci.org/atc-/docker-registry-web.svg?branch=master)](https://travis-ci.org/atc-/docker-registry-web)

docker-registry-web
===================

A web UI for easy private/local Docker Registry integration. Allows you to browse, delete and search for images as
well as register multiple registries for large installations.

## Features

The application boasts the following features:

 * View all images for all registries (one to many)

 * Delete images

 * Search for images

 * Containerized via [Docker](https://registry.hub.docker.com/u/atcol/docker-registry-web/)

 * Exposes its database as a volume 

## Demo

This project is containerized. You can run with docker right now by simply running:

	docker run -p 8080:8080 atcol/docker-registry-web

and browsing to localhost:8080/.

You can also hold the internal database's contents on the host machine using the `-v` or `--volumes-from` flag:

	docker run -p 8080:8080 -v /some/data/dir:/var/lib/h2 atcol/docker-registry-web

and the registry configurations and other data will be stored on the host machine, which survives container restarts.

You could also use the data-container pattern using `--volumes-from`:

	docker run -v /var/lib/h2 --name="registry_web_data" ubuntu

	docker run -p 8080:8080 --volumes-from=registry_web_data atcol/docker-registry-web

Now all data will be kept in the `registry_web_data` container.

## Building

Build the web-app with `grails war docker/docker-registry-web.war`. Now you build the docker image:

	docker build docker/

and run it:
	
	docker run -dt -p 8080:8080 <image id from last command>

then browse to localhost:8080/registry/index and create a new registry (using the host's external
IP).
