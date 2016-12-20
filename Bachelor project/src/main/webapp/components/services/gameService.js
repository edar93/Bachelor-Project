'use strict'

var gameService = function (backendGateway, $q) {

    this.getUser = createGame;

    function createGame() {
        return backendGateway.get('GET_USER', null, true)
    }
};

angular.module('portRoyalApp.gameService', [])
    .service('gameService', gameService);