'use strict';

var gameService = function (backendGateway) {

    this.createGame = createGame;
    this.getBaseGameStatus = getBaseGameStatus;
    this.getAllGamesToJoin = getAllGamesToJoin;
    this.joinGame = joinGame;

    function joinGame(owner){
        return backendGateway.put('JOIN_GAME', owner);
    }

    function getBaseGameStatus(){
        return backendGateway.get('BASIC_GAME_STATUS')
    }

    function getAllGamesToJoin(){
        return backendGateway.get('GET_ALL_GAMES_IN_QUEUE')
            .then(function(response){
                if(response){
                    return response.data;
                }
            });
    }

    function createGame() {
        return backendGateway.post('CREATE_NEW_GAME')
    }
};

angular.module('portRoyalApp.gameService', [])
    .service('gameService', gameService);