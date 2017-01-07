'use strict';

var gameStatusService = function (backendGateway, gameService) {

    this.updateGame = updateGame;

    function updateGame(user){
        gameService.getPlayersGame(user)
            .then(function (data) {
                console.log('data', data);
                backendGateway.post('TEST', data, undefined, true).then(
                    function (resoponse) {
                        console.log(resoponse);
                    }
                );
            }
        );
    }
};

angular.module('portRoyalApp.gameStatusService', [])
    .service('gameStatusService', gameStatusService);