'use strict';

var gameService = function (backendGateway) {

    this.createGame = createGame;
    this.getBaseGameStatus = getBaseGameStatus;

    function getBaseGameStatus(){
        return backendGateway.get('BASIC_GAME_STATUS')
    }

    function createGame() {
        return backendGateway.post('CREATE_NEW_GAME')
    }
};

angular.module('portRoyalApp.gameService', [])
    .service('gameService', gameService);