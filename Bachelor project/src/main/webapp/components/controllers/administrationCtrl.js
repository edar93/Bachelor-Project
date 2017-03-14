'use strict';

administrationCtrl
    .controller('administrationCtrl', function ($scope, baseInitService, administrationService) {

        baseInitService.setVariables($scope);
        baseInitService.init();

        administrationService.initPage($scope);
    });