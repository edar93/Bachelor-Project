'use strict';

var globalChatService = function (stompService) {

    this.initChat = initChat;
    this.disconnect = disconnect;

    var notSubscribing;
    var notConnected;

    function disconnect() {
        //TODO does not work - scope not variable of
        scope.chat.disconnect();
    }

    function initChat(scope) {
        notSubscribing = true;
        notConnected = true;
        scope.messages = [];
        scope.text = "";
        scope.chat = stompService('/port-royal/old-backend/chat');

        if (notConnected) {
            notConnected = false;
            scope.chat.connect("not needed username", "not needed password", function () {
                if (notSubscribing) {
                    notSubscribing = false;
                    scope.chat.subscribe("/messages", function (message) {
                        var body = JSON.parse(message.body);
                        scope.messages.push(body.author + ": " + body.text);
                    });
                }
            });
        }
        scope.sendName = function () {
            console.log(scope, 'scope');
            scope.chat.send("/port-royal/old-backend/sendMessage", {}, JSON.stringify({
                'text': scope.text,
                'author': scope.user
            }));
        }
    }
};

angular.module('portRoyalApp.globalChatService', [])
    .service('globalChatService', globalChatService);