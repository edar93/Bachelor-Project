'use strict';

var gameActionService = function (backendGateway, gameStatusService, loginService, locationService, gameService, stompService) {

    this.init = init;

    var game;
    var gameId;
    var notSubscribing;

    function setToScope(scope) {
        scope.faceCard = faceCard;
        scope.pickCard = pickCard;
        scope.pickExpedition = pickExpedition;
        scope.skipAction = skipAction;
        scope.evaluateAdmirals = evaluateAdmirals;
        scope.showResults = showResults;
    }

    function showResults() {
        backendGateway.post('GAME_END')
            .then(function (response) {
                locationService.showStatsRecord(response.data);
            })
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
        gameId = data.id;
        var url = '/myGame/' + gameId;

        game = stompService('/port-royal/old-backend/game');

        game.connect('not needed username', 'not needed password', function () {
            if (notSubscribing) {
                notSubscribing = false;
                game.subscribe(url, function (response) {
                    gameStatusService.updateGame();
                });
            }
        });
    }

    function globalUpdate() {
        //TODO message is not needed
        game.send("/port-royal/old-backend/sendAction/" + gameId, {}, JSON.stringify({'text': 'does not matter'}));
    }

    function skipAction(){
        backendGateway.post('SKIP_ACTION')
            .then(function () {
                globalUpdate();
            })
    }

    function evaluateAdmirals() {
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