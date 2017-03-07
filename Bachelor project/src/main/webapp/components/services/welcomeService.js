'use strict';

var welcomeService = function (gameService) {

    this.updateGamesForJoin = updateGamesForJoin;
    this.init = init;

    var localScope;

    function init(scope) {
        localScope = scope;
    }

    function updateGamesForJoin() {
        gameService.getAllGamesToJoin()
            .then(function (data) {
                localScope.gamesForJoin = data;
            }
        );
    }
};

angular.module('portRoyalApp.welcomeService', [])
    .service('welcomeService', welcomeService);
