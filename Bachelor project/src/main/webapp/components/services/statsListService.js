'use strict';

var statsListService = function (backendGateway, locationService) {

    this.initPage = initPage;

    var localScope;

    function initPage(scope, playerLogin) {
        localScope = scope;
        scope.showGame = showGame;
        backendGateway.get('STATS_PLAYER', undefined, undefined, playerLogin)
            .then(setData);
    }

    function setData(response) {
        localScope.gamesList = response.data;
    }

    function showGame(gameId) {
        locationService.showStatsRecord(gameId);
    }

};

angular.module('portRoyalApp.statsListService', [])
    .service('statsListService', statsListService);