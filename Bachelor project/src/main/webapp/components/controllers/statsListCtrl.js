'use strict';

statsListCtrl
    .controller('statsListCtrl', function ($scope, baseInitService) {

        baseInitService.setVariables($scope);
        baseInitService.init();
    }
);