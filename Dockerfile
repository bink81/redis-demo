FROM java:8

COPY ./target /var/www/java
WORKDIR /var/www/java

RUN java -jar redis-demo-0.0.1-SNAPSHOT.jar
CMD ["java", "Application"]

EXPOSE 8080