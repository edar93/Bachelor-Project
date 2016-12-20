'use strict';

var gameService = function (backendGateway, $q) {

    this.createGame = createGame;

    function createGame() {
        return backendGateway.post('CREATE_NEW_GAME')
    }
};

angular.module('portRoyalApp.gameService', [])
    .service('gameService', gameService);