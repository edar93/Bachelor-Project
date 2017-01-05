'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, backendGateway, loginService, gameService) {


        gameService.getPlayersGame(loginService.getUser())
            .then(function (data) {
                console.log('data', data);
                backendGateway.post('TEST', JSON.stringify(data)).then(
                    function (resoponse) {
                        console.log(resoponse);
                    }
                );
            }
        );


    });