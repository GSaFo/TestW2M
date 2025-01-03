FROM tomcat:11.0-jdk21

RUN rm -rf /usr/local/tomcat/webapps/*

COPY target/TestW2M-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]