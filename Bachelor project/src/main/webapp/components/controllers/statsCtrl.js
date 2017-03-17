'use strict';

statsCtrl
    .controller('statsCtrl', function ($scope, $routeParams, baseInitService) {

        console.log($routeParams, 'route param');

        baseInitService.setVariables($scope);
        baseInitService.init();
    }
);