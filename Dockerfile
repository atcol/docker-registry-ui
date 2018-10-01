FROM    ubuntu

# Install java and tomcat
RUN     apt-get update && apt-get install -y tomcat8 openjdk-8-jdk curl
RUN     mkdir /var/lib/h2 && chmod a+rw /var/lib/h2
RUN     rm -rf /var/lib/tomcat8/webapps/*
ENV     JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64/

# Install grails and project dependencies
WORKDIR /work
ADD     grailsw.sh /work/grailsw.sh
ADD     wrapper /work/wrapper
ADD     application.properties /work/application.properties
ADD     grails-app/conf/BuildConfig.groovy /work/grails-app/conf/BuildConfig.groovy
RUN     ./grailsw.sh help

# Add project files and build a war
ADD     . /work
RUN     ./grailsw.sh war
RUN     cp target/docker-registry-ui-*.war /var/lib/tomcat8/webapps/ROOT.war

# Update catalina configuration
WORKDIR /usr/share/tomcat8/bin/
ADD     startup.sh /usr/share/tomcat8/bin/custom-startup.sh
RUN     chmod +x /usr/share/tomcat8/bin/custom-startup.sh

# Clean up the installation
RUN apt-get purge -y curl

EXPOSE  8080
VOLUME  ["/var/lib/h2/", "/var/lib/tomcat8"]
ENV     CATALINA_BASE /var/lib/tomcat8
CMD     /usr/share/tomcat8/bin/custom-startup.sh

