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

 * Containerized via [Docker](https://registry.hub.docker.com/u/atcol/docker-registry-ui/)

 * Exposes the webapp's configuration data as a volume for use with data-containers or simple host directory storage

## Demo

This project is containerized. You can run with docker right now by simply running:

	docker run -p 8080:8080 atcol/docker-registry-web

and browsing to localhost:8080/.

The webapp's configuration data is stored inside the container in a H2 database under `/var/lib/h2/`. You can hold this data on the host machine using the `-v` flag:

	docker run -p 8080:8080 -v /some/data/dir:/var/lib/h2 atcol/docker-registry-web

which survives container restarts.

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
