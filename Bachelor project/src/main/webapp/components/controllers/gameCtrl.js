'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, loginService, gameStatusService, gameActionService) {

        gameStatusService.setScopeAndPlayer($scope, loginService.getUser());
        gameStatusService.updateGame();

        gameActionService.init($scope);
    })
;