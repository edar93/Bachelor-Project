'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, gameService, loginService, locationService, baseInitService, gameCreationService) {

        baseInitService.setVariables($scope);
        baseInitService.init();

        gameCreationService.initAndSetScope($scope);
        gameCreationService.setSlider($scope);
    });