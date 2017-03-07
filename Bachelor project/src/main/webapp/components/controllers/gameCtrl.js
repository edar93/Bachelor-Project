'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, loginService, gameStatusService, gameActionService, baseInitService) {

        baseInitService.setVariables($scope);
        baseInitService.init();

        loginService.getUser()
            .then(function(user){
                gameStatusService.setScopeAndPlayer($scope, user);
                gameStatusService.updateGame();
        });


        gameActionService.init($scope);
    })
;