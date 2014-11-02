if [ -n "$APP_CONTEXT" ]; then
  mv /var/lib/tomcat7/webapps/ROOT.war /var/lib/webapps/${APP_CONTEXT}.war
fi
sed -i '1iexport CATALINA_OPTS=" -Djava.security.egd=file:/dev/./urandom -Djava.net.preferIPv6Addresses=true "' bin/catalina.sh
bin/catalina.sh run

