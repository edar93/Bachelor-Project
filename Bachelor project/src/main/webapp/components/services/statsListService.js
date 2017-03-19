'use strict';

var statsListService = function (backendGateway, locationService) {

    this.initPage = initPage;

    var localScope;

    function initPage(scope, playerLogin) {
        localScope = scope;
        scope.showGame = showGame;
        backendGateway.get('STATS_PLAYER', undefined, undefined, playerLogin)
            .then(setData, badResponse);
    }

    function setData(response) {
        console.log(response, 'succ');
        localScope.gamesList = response.data;
    }

    function badResponse(response) {
        localScope.playerDoesNotExist = true;
    }

    function showGame(gameId) {
        locationService.showStatsRecord(gameId);
    }

};

angular.module('portRoyalApp.statsListService', [])
    .service('statsListService', statsListService);