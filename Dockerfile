FROM ubuntu:14.04

RUN apt-get install tomcat7 openjdk-7-jdk

RUN mkdir /var/lib/h2

RUN chmod a+rw /var/lib/h2

RUN rm -rf /var/lib/tomcat7/webapps/*

VOLUME ["/var/lib/h2/", "/var/lib/tomcat7"]

ADD http://atc.gd/docker-registry-ui.war /var/lib/tomcat7/webapps/ROOT.war

CMD sed -i '1iexport CATALINA_OPTS=" -Djava.security.egd=file:/dev/./urandom "' bin/catalina.sh && bin/catalina.sh run
