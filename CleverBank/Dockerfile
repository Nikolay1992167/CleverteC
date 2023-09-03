FROM gradle:7.2-jdk17 AS build

WORKDIR /app

COPY . .

RUN gradle war

FROM tomcat:9.0.54-jdk17-openjdk

RUN rm -rf /usr/local/tomcat/webapps/*

COPY --from=build /app/build/libs/CleverBank-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

#CMD ["catalina.sh", "run"]