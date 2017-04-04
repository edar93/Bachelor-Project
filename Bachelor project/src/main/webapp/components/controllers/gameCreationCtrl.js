'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, baseInitService, gameCreationService) {

        baseInitService.setVariables($scope);
        baseInitService.init();

        gameCreationService.initAndSetScope($scope);
        gameCreationService.setSlider($scope);
    });