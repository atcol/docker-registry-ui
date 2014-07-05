FROM astral1/tomcat7

RUN mkdir /var/lib/h2

RUN chmod a+rw /var/lib/h2

RUN rm -rf /tomcat7/webapps/*

VOLUME ["/var/lib/h2/", "/tomcat7"]

ADD http://atc.gd/docker-registry-ui.war /tomcat7/webapps/ROOT.war
