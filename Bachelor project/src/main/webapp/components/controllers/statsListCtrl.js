'use strict';

statsListCtrl
    .controller('statsListCtrl', function ($scope, $routeParams, statsListService, baseInitService) {

        baseInitService.setVariables($scope);
        baseInitService.init();

        statsListService.initPage($scope, $routeParams.player, $routeParams.page);
    }
);