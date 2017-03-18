'use strict';

var baseInitService = function ($route, backendGateway, loginService, locationService) {

    this.setVariables = setVariables;
    this.init = init;

    var localScope;

    function init() {
        locationService.startLocationCheck();
    }

    function setVariables(scope) {
        locationService.tellScopeLocationStatus(scope);
        loginService.getUser()
            .then(function (user) {
                scope.user = user;
                scope.logout = logout;
            });
        backendGateway.get('IS_ADMIN')
            .then(function (response) {
                scope.isAdmin = response.data;
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