[![Build Status](https://travis-ci.org/atc-/docker-registry-web.svg?branch=master)](https://travis-ci.org/atc-/docker-registry-web) [![Gitter chat](https://badges.gitter.im/atc-/docker-registry-web.png)](https://gitter.im/atc-/docker-registry-web)

Docker Registry UI
===================

A web UI for easy private/local Docker Registry integration. Allows you to browse, delete and search for images as
well as register multiple registries for large installations.

## Features

The application boasts the following features:

 * View all images for all registries (one to many)
 
 * Further info. page for images for inspection of config. etc
 
 * Pull copy/paste shortcuts

 * Delete images

 * Search for images

 * Containerized via [Docker](https://registry.hub.docker.com/u/atcol/docker-registry-ui/)
 
 * Custom deployment contest

 * Exposes the webapp's configuration data as a volume for use with data-containers or simple host directory storage

## Demo

This project is containerized. You can run with docker right now by simply running:

	docker run -p 8080:8080 atcol/docker-registry-ui

and browsing to localhost:8080/.

## Application context

Sometimes you may want to run the app in custom context, say `http://server:8080/webui`. To do so, supply the optional variable `APP_CONTEXT`.

	docker run -p 8080:8080 -e APP_CONTEXT=/webui atcol/docker-registry-ui
        
## Statelessness

The app' requires registry configuration which can be supplied once the app's running, or through container environment
variables:

	docker run -p 8080:8080 -e REG1=http://dev:5000/v1/ -e REG2=http://prod/v1/ atcol/docker-registry-ui

which will run the application and automatically register two registries at the hosts `dev` and `prod` respectively,
both running API versions v1. You must provide URLs that include the API version. 

**Note:** don't use `localhost` in registry URLs! The host needs to be visible from inside the container, so `-e REG1=http://localhost/...` won't work because localhost will resolve to the container's IP. If registry is running on the host, then remember to use its IP: `172.17.42.1`.

## Volumes

The webapp's configuration data is stored inside the container in a H2 database under `/var/lib/h2/`. You can hold this data on the host machine using the `-v` flag:

	docker run -p 8080:8080 -v /some/data/dir:/var/lib/h2 atcol/docker-registry-ui

which survives container restarts.

You could also use the data-container pattern using `--volumes-from`:

	docker run -v /var/lib/h2 --name="registry_web_data" ubuntu

	docker run -p 8080:8080 --volumes-from=registry_web_data atcol/docker-registry-ui

Now all data will be kept in the `registry_web_data` container.

# Custom Deployment Context

You can deploy this container in a custom context as such:

`docker run -p 80:8080 -it -e APP_CONTEXT=ui -e REG1=http://172.17.42.1:5000/v1/ atcol/docker-registry-ui`

will expose the container under `http://localhost/ui`.

# License

As of release 0.9.5, this project is licenced under GPL v3.0. See the LICENSE file.

# Troubleshooting

## My registry can't be found/seen by docker registry web! Help!

If you're seeing error messages like `Connection to http://registry refused`, make sure you've configured the port and hostname correctly. **Do not** set the registry hostname to `localhost`, because the container will resolve this to itself (127.0.0.1). If the registry instance runs on the host machine (that which runs the container), then set the registry IP/hostname to `172.17.42.1`. Alternatively, you could use writable /etc/hosts as of Docker 1.2 and 'route' it that way.

## Registries behind HTTPS or `Ping failed: javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated`
You need to register the CA with the container's keystore; see issue #108.
