FROM openjdk:8-jdk-alpine

MAINTAINER bink81

VOLUME /tmp

ADD redis-demo-0.0.1-SNAPSHOT.jar application/app.jar

RUN touch application/app.jar

ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","application/app.jar"]

EXPOSE 8080