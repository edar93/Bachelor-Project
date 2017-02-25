'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, gameService, loginService, locationService, baseInitService, gameCreationService) {


        $scope.slider = {
            value: 5,
            options: {
                floor: 0,
                ceil: 10,
                showTicks: true
            }
        };

        baseInitService.setVariables($scope);
        gameCreationService.initAndSetScope($scope);
    });