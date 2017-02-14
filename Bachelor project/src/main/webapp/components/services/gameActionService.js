'use strict';

var gameActionService = function (backendGateway, gameStatusService, loginService, gameService, stompService) {

    this.init = init;

    var game;
    var gameOwner;
    var notSubscribing;

    function setToScope(scope) {
        scope.faceCard = faceCard;
        scope.pickCard = pickCard;
        scope.pickExpedition = pickExpedition;
        scope.skipAction = skipAction;
        scope.evaluateAdmirals = evaluateAdmirals;
    }

    function init(scope) {
        initWebSockets();
        setToScope(scope);
    }

    function initWebSockets() {
        notSubscribing = true;
        loginService.getUser()
            .then(gameService.getPlayersGame)
            .then(startSockets);
    }

    function startSockets(data) {
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
    }

    function globalUpdate() {
        //TODO message is not needed
        game.send("/port-royal/sendAction/" + gameOwner, {}, JSON.stringify({'text': 'does not matter'}));
    }

    function skipAction(){
        //TODO add implementation
        backendGateway.post('SKIP_ACTION')
            .then(function () {
                globalUpdate();
            })
    }

    function evaluateAdmirals() {
        //TODO add implementation
        backendGateway.post('EVALUATE_ADMIRALS')
            .then(function () {
                globalUpdate();
            })
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

    function pickExpedition(id) {
        backendGateway.post('PICK_EXPEDITION', id, undefined, true)
            .then(function () {
                globalUpdate();
            })
    }
};
angular.module('portRoyalApp.gameActionService', [])
    .service('gameActionService', gameActionService);