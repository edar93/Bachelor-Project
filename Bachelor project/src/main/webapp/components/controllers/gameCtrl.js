'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, loginService, gameStatusService, gameActionService) {

        $scope.faceCard = gameActionService.faceCard;

        gameStatusService.setScope($scope);
        gameStatusService.updateGame(loginService.getUser());

    });