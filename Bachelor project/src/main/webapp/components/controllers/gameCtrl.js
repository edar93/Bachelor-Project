'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, loginService, gameStatusService, gameActionService, stompService, gameService) {

        var chat = stompService('/port-royal/game');

        console.log(loginService.getUser(), 'user');

        gameService.getPlayersGame(loginService.getUser())
            .then(function (data) {
                var gameOwner = data.owner;
                var url = '/myGame/' + gameOwner;
                console.log(url, 'url');
                console.log(gameOwner, 'gameOwner');

                chat = stompService('/port-royal/game');

                chat.connect('not needed username', 'not needed password', function () {
                    chat.subscribe(url, function (message) {
                        console.log(message, 'get websocket clean');
                        console.log(JSON.parse(message.body), 'get websocket body');
                    });
                });
            });

        $scope.sendName = function () {
            chat.send("/port-royal/sendAction/adam", {}, JSON.stringify({'text': 'test'}));
        };


        $scope.faceCard = gameActionService.faceCard;
        $scope.pickCard = gameActionService.pickCard;

        gameStatusService.setScopeAndPlayer($scope, loginService.getUser());
        gameStatusService.updateGame(loginService.getUser());

    })
;