'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, backendGateway, loginService, gameStatusService) {

        gameStatusService.setScope($scope);
        gameStatusService.updateGame(loginService.getUser());


    });