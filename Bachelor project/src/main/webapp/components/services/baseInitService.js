'use strict';

var baseInitService = function ($route, backendGateway, loginService, locationService) {

    this.setVariables = setVariables;
    this.init = init;

    function init() {
        locationService.startLocationCheck();
    }

    function setVariables(scope) {
        scope.searchForPlayer = searchForPlayer;
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

    function searchForPlayer(searchPlayer) {
        locationService.showPlayersStats(searchPlayer);
    }

    function logout() {
        locationService.goToWelcome();
        $route.reload();
    }

};

angular.module('portRoyalApp.baseInitService', [])
    .service('baseInitService', baseInitService);