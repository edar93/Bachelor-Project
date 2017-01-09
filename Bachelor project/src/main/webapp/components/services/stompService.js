angular
    .module('portRoyalApp.stompService', []).
    factory('stompService',['$rootScope', function($rootScope) {

        var client = {};

        var service = function(url){

            var socket = new SockJS(url);
            client = Stomp.over(socket);

            return {
                subscribe: subscribe,
                send: send,
                connect: connect,
                disconnect: disconnect
            }
        };

        return service;

        function subscribe(queue, callback) {
            client.subscribe(queue, function() {
                var args = arguments;
                $rootScope.$apply(function() {
                    callback(args[0]);
                })
            })
        }

        function send(queue, headers, data){
            client.send(queue, headers, data);
        }

        function connect(user, password, on_connect, on_error, vhost) {
            client.connect(user, password,
                function(frame) {
                    $rootScope.$apply(function() {
                        on_connect.apply(client, frame);
                    })
                },
                function(frame) {
                    $rootScope.$apply(function() {
                        on_error.apply(client, frame);
                    })
                }, vhost);

        }

        function disconnect(callback){
            client.disconnect(function() {
                $rootScope.$apply(function() {
                    callback.apply(args);
                })
            })
        }
    }]
);