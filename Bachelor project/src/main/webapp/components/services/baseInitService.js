'use strict';

var baseInitService = function ($route, loginService, locationService) {

    this.setVariables = setVariables;
    this.init = init;

    var localScope;

    function init() {
        locationService.startLocationCheck();
    }

    function setVariables(scope) {
        loginService.getUser()
            .then(function (user) {
                scope.user = user;
                scope.logout = logout;
            });
    }

    function logout() {
        console.log(loginService.logout(), 'user logged out');
        locationService.goToWelcome();
        $route.reload();
    }

};

angular.module('portRoyalApp.baseInitService', [])
    .service('baseInitService', baseInitService);