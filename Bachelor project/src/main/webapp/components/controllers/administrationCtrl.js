'use strict';

administrationCtrl
    .controller('administrationCtrl', function ($scope, gameService, loginService, locationService, baseInitService, gameCreationService) {

        baseInitService.setVariables($scope);
        baseInitService.init();

        //gameCreationService.initAndSetScope($scope);
    });