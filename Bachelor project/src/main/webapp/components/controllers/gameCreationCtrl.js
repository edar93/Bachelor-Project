'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, gameService, loginService, locationService, baseInitService, gameCreationService) {

        baseInitService.setVariables($scope);
        gameCreationService.initAndSetScope($scope);
        gameCreationService.setSlider($scope);
    });