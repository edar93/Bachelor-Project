'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, loginService, gameStatusService, gameActionService) {

        $scope.faceCard = gameActionService.faceCard;
        $scope.pickCard = gameActionService.pickCard;

        gameStatusService.setScopeAndPlayer($scope, loginService.getUser());
        gameStatusService.updateGame(loginService.getUser());

    });