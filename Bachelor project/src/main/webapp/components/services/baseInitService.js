'use strict';

var baseInitService = function (loginService, locationService) {

    this.setVariables = setVariables;

    var localScope;

    function setVariables(scope) {
        localScope = scope;
        scope.testVARiable = 'test';
    }

};

angular.module('portRoyalApp.baseInitService', [])
    .service('baseInitService', baseInitService);