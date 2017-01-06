'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, backendGateway, loginService, gameStatusService) {


        gameStatusService.updateGame(loginService.getUser());



    });