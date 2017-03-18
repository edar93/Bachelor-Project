'use strict';

statsCtrl
    .controller('statsCtrl', function ($scope, $routeParams, statsService, baseInitService) {
        baseInitService.setVariables($scope);
        baseInitService.init();

        statsService.initPage($scope, $routeParams.game);
    }
);