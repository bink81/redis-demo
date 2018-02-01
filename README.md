# redis-demo
This is a simple demo of a REST API that send and receives messages to Redis, persists them in a dB and notifies WebSockets. The optional deployment is done via Docker. 

## Installation

### To start a local web server alone (not useful without Redis and dB though):
```
mvn spring-boot:run
```

### To build the jar and deploy the 'production' system:
```
mvn -DskipTests clean package
docker-compose up
```

### To deploy the integration testing setup:

```
docker-compose -f .\docker-compose-test.yml up
```

## Operations

### View/add messages

When started, the system has a REST API that supports GET and POST requests:

http://localhost:8080/v1/messages/

In addition this URL tracks all incoming messages. Note that a connection is closed when a server restarts. 

http://localhost:8080/index.html

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
