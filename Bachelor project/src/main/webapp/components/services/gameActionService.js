'use strict';

var gameActionService = function(backendGateway, gameStatusService){
    this.faceCard = faceCard;

    function faceCard(){
        backendGateway.post('FACE_CARD')
            .then(function(){
                gameStatusService.updateGame();
            })
    }
};

angular.module('portRoyalApp.gameActionService', [])
    .service('gameActionService', gameActionService);