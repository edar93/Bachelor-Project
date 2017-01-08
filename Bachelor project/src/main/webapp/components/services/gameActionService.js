'use strict';

var gameActionService = function(backendGateway, gameStatusService){

    this.faceCard = faceCard;
    this.pickCard = pickCard;

    function faceCard(){
        backendGateway.post('FACE_CARD')
            .then(function(){
                gameStatusService.updateGame();
            })
    }

    function pickCard(id){
        backendGateway.post('PICK_CARD', id, undefined, true)
            .then(function(){
                gameStatusService.updateGame();
            })
    }
};

angular.module('portRoyalApp.gameActionService', [])
    .service('gameActionService', gameActionService);