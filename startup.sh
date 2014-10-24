if [ -n "$APP_CONTEXT" ]; then
  mv /tomcat7/webapps/ROOT.war /tomcat7/webapps/${APP_CONTEXT}.war
fi
sed -i '1iexport CATALINA_OPTS=" -Djava.security.egd=file:/dev/./urandom "' bin/catalina.sh
bin/catalina.sh run

