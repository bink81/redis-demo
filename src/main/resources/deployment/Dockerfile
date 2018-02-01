# This configuration creates a production (demo) web server image.
# It's possibly a hack to put it in "deployment" directory, but I need the parent directory to contain a Dockerfile configuration image with integration tests.

FROM openjdk:8-jdk-alpine

MAINTAINER bink81

VOLUME /tmp

ADD redis-demo-0.0.1-SNAPSHOT.jar application/app.jar

RUN touch application/app.jar

ENTRYPOINT ["java","-jar","-Djava.security.egd=file:/dev/./urandom","application/app.jar"]

EXPOSE 8080