'use strict';

var statsListService = function (backendGateway, locationService) {

    this.initPage = initPage;

    var localScope;

    function initPage(scope, playerLogin, page) {
        localScope = scope;
        localScope.player = playerLogin;
        localScope.showPage = showPage;
        localScope.currentPage = page;
        scope.showGame = showGame;
        backendGateway.get('STATS_PLAYER', undefined, undefined, playerLogin + '/' + page)
            .then(setData, badResponse);

        backendGateway.get('STATS_LIST_PAGES', undefined, undefined, playerLogin)
            .then(function (response) {
                localScope.pagesCount = createArray(response.data);
            });
    }

    function showPage(player, page) {
        locationService.showPlayersStats(player, page);
    }

    function createArray(size) {
        var output = [];
        for (var i = 0; i < size; i++) {
            output.push(i);
        }
        return output
    }

    function setData(response) {
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