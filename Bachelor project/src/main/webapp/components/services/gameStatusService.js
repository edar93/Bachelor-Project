'use strict';

var gameStatusService = function (backendGateway, gameService) {

    this.updateGame = updateGame;
    this.setScope = setScope;

    var localUser;
    var playersList;
    var table;
    var playersCount;
    var localPlayer;

    var gameScope;

    function setScope(scope){
        gameScope = scope;
    }

    function updateGame(user){
        localUser = user;
        gameService.getPlayersGame(user)
            .then(function (data) {
                backendGateway.post('TEST', data, undefined, true).then(
                    function (resoponse) {
                        var game = resoponse.data;
                        parseGameToService(game);
                        setToScope();
                        console.log(game);
                    }
                );
            }
        );
    }

    function parseGameToService(game){
        for(var i = game.players.length - 1; i >= 0; i--){
            if(game.players[i].login == localUser){
                localPlayer = game.players[i];
                game.players.splice(i,1);
            }
        }

        playersList = game.players;
        table = game.table.cards;
        playersCount = game.playersCount;
    }

    function setToScope(){
        gameScope.table = table;
        gameScope.playersCount = playersCount;
        gameScope.playersList = playersList;
        gameScope.localPlayer = localPlayer;
    }
};

angular.module('portRoyalApp.gameStatusService', [])
    .service('gameStatusService', gameStatusService);