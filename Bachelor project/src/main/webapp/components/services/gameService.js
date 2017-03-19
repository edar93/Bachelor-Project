'use strict';

var gameService = function (backendGateway) {

    this.createGame = createGame;
    this.getPlayersGame = getPlayersGame;
    this.getAllGamesToJoin = getAllGamesToJoin;
    this.joinGame = joinGame;
    this.startGame = startGame;

    function joinGame(id) {
        return backendGateway.put('JOIN_GAME', id);
    }

    function getPlayersGame(player) {
        return backendGateway.post('GET_PLAYERS_GAME', player)
            .then(function (response) {
                if (response) {
                    return response.data;
                }
            });
    }

    function getAllGamesToJoin() {
        return backendGateway.get('GET_ALL_FREE_GAMES_IN_QUEUE')
            .then(function (response) {
                if (response) {
                    return response.data;
                }
            });
    }

    function createGame() {
        return backendGateway.post('CREATE_NEW_GAME')
    }

    function startGame() {
        return backendGateway.post('START_GAME');
    }
};
angular.module('portRoyalApp.gameService', [])
    .service('gameService', gameService);