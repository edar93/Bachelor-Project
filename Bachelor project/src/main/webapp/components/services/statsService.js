'use strict';

var statsService = function (backendGateway) {

    this.initPage = initPage;

    var imageFormat = '.jpg';
    var localScope;

    function initPage(scope, gameId) {
        localScope = scope;
        backendGateway.get('STATS_GAME', undefined, undefined, gameId)
            .then(setData);
    }

    function setData(response) {
        localScope.gameStats = response.data;
        transformPlayers(localScope.gameStats.playerList);
    }

    function transformPlayers(playerList) {
        for (var player in playerList) {
            console.log(playerList[player].cards, 'transf');
            playerList[player].cards = transformCards(playerList[player].cards);
        }
    }

    function transformCards(cardList) {
        for (var card in cardList) {
            cardList[card].image = cardList[card].cardType + '_' + cardList[card].coin + '_' + cardList[card].influence + imageFormat;
        }
        return cardList;
    }
};

angular.module('portRoyalApp.statsService', [])
    .service('statsService', statsService);