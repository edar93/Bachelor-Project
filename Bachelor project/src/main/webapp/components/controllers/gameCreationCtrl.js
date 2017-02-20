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

        $scope.maxPlayers = 1;
        $scope.$watch('maxPlayers', maxPlayersChange, true)

        function maxPlayersChange(newValue) {
            console.log('was aaaaaaaaaaaaa');
            console.log(newValue);
            console.log('was aaaaaaaaaaaaaa');
        }

    });