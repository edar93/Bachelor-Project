'use strict';

var gameActionService = function (backendGateway, gameStatusService, loginService, gameService, stompService) {

    this.init = init;

    var game;
    var gameOwner;
    var notSubscribing;

    function init(scope) {
        initWebSockets();
        setToScope(scope);
    }

    function setToScope(scope) {
        scope.faceCard = faceCard;
        scope.pickCard = pickCard;
    }

    function initWebSockets() {
        notSubscribing = true;
        gameService.getPlayersGame(loginService.getUser())
            .then(function (data) {
                gameOwner = data.owner;
                var url = '/myGame/' + gameOwner;

                game = stompService('/port-royal/game');

                game.connect('not needed username', 'not needed password', function () {
                    if (notSubscribing) {
                        notSubscribing = false;
                        game.subscribe(url, function (response) {
                            //TODO remove
                            //console.log(JSON.parse(response.body), 'get websocket body');
                            if (response && response.body) {
                                gameStatusService.updateGame(JSON.parse(response.body));
                            }
                        });
                    }
                });
            });
    }

    function globalUpdate() {
        //TODO message is not needed
        game.send("/port-royal/sendAction/" + gameOwner, {}, JSON.stringify({'text': loginService.getUser()}));
    }

    function faceCard() {
        backendGateway.post('FACE_CARD')
            .then(function () {
                globalUpdate();
            })
    }

    function pickCard(id) {
        backendGateway.post('PICK_CARD', id, undefined, true)
            .then(function () {
                globalUpdate();
            })
    }
};
angular.module('portRoyalApp.gameActionService', [])
    .service('gameActionService', gameActionService);