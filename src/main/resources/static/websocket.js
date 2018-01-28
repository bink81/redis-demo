function connect() {
    var socket = new SockJS('/gs-guide-websocket');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        stompClient.subscribe('/topic/messages', function (message) {
            message = JSON.parse(message.body).text;
            $("#messages").append("<tr><td>" + message + "</td></tr>");
        });
    });
}

connect();