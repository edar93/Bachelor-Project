'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, loginService, gameStatusService, gameActionService, baseInitService) {

        baseInitService.setVariables($scope);

        loginService.getUser()
            .then(function(user){
                gameStatusService.setScopeAndPlayer($scope, user);
                gameStatusService.updateGame();
        });


        gameActionService.init($scope);
    })
;