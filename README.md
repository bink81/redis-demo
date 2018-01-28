# redis-demo
This is a simple demo of a REST API that send and receives messages to Redis, persists them in a dB and notifies WebSockets

## Installation

### To start local server:
```
mvn spring-boot:run
```

### To package:
```
mvn package
```

## Operations

### View/add messages

When started, the system has a REST API that supports GET and POST requests:

http://localhost:8080/v1/messages/

In addition a URL tracks all incoming messages. Note that a connection is closed when a server restarts. 

http://localhost:8080/index.html

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
