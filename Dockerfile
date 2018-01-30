FROM maven:3.5.2-jdk-8
MAINTAINER bink81

ADD . /usr/local/message_server

RUN cd /usr/local/message_server && mvn -DskipTests clean package && ls /usr/local/message_server/target

CMD ["java", "-jar", "/usr/local/message_server/target/redis-demo-0.0.1-SNAPSHOT.jar"]

EXPOSE 8080