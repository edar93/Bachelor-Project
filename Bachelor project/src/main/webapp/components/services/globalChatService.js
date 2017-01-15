'use strict';

var globalChatService = function (stompService) {

    this.initChat = initChat;
    this.disconnect = disconnect;

    function disconnect(){
        stompService.disconnect();
    }

    function initChat(scope){
        scope.messages = [];
        scope.text = "";
        scope.chat = stompService('/port-royal/chat');

        scope.chat.connect("not needed username", "not needed password", function(){
            scope.chat.subscribe("/topic/messages", function(message) {
                var body = JSON.parse(message.body);
                scope.messages.push(body.text);
            });
        });

        scope.sendName = function(){
            scope.chat.send("/port-royal/sendMessage", {}, JSON.stringify({ 'text': scope.text }));
        }
    }


};

angular.module('portRoyalApp.globalChatService', [])
    .service('globalChatService', globalChatService);