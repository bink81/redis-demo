# This configuration creates an integration test image
# It's probably a hack to put it here (while the production configuration ends up in "target" directory), 
# but I need the pom file in the Docker context to run tests in a Docker container.
# The 'production' Dockerfile is in src/main/resources and get copied during packaging.

FROM maven:3.5.2-jdk-8

MAINTAINER bink81

VOLUME /tmp

ADD . ./application

WORKDIR /application

ENTRYPOINT mvn clean verify

EXPOSE 8080