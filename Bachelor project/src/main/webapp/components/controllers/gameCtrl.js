'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, loginService, gameStatusService, gameActionService) {

        $scope.faceCard = gameActionService.faceCard;

        gameStatusService.setScopeAndPlayer($scope, loginService.getUser());
        gameStatusService.updateGame(loginService.getUser());

    });