FROM    ubuntu:14.04

# Install java and tomcat
RUN     apt-get update && apt-get install -y tomcat7 openjdk-7-jdk
RUN     mkdir /var/lib/h2 && chmod a+rw /var/lib/h2
RUN     rm -rf /var/lib/tomcat7/webapps/*
ENV     JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64/

# Install grails and project dependencies
WORKDIR /work
ADD     grailsw /work/grailsw
ADD     wrapper /work/wrapper
ADD     application.properties /work/application.properties
ADD     grails-app/conf/BuildConfig.groovy /work/grails-app/conf/BuildConfig.groovy
RUN     ./grailsw help

# Add project files and build a war
ADD     . /work
RUN     ./grailsw war
RUN     cp target/docker-registry-ui-*.war /var/lib/tomcat7/webapps/ROOT.war

# Update catalina configuration
WORKDIR /usr/share/tomcat7/bin/
ADD     startup.sh /usr/share/tomcat7/bin/custom-startup.sh
RUN     chmod +x /usr/share/tomcat7/bin/custom-startup.sh

EXPOSE  8080
VOLUME  ["/var/lib/h2/", "/var/lib/tomcat7"]
ENV     CATALINA_BASE /var/lib/tomcat7
CMD     /usr/share/tomcat7/bin/custom-startup.sh

