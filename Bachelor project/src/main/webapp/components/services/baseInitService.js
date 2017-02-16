'use strict';

var baseInitService = function (loginService, locationService) {

    this.setVariables = setVariables;
    this.init = init;

    var localScope;

    function init() {
        locationService.startLocationCheck();
    }

    function setVariables(scope) {
        localScope = scope;


        scope.testVARiable = 'test';
    }

};

angular.module('portRoyalApp.baseInitService', [])
    .service('baseInitService', baseInitService);