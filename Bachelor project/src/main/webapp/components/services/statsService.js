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
            playerList[player].cards = transformCards(playerList[player].cards);
        }
    }

    function transformCards(cardList) {
        for (var card in cardList) {
            console.log(cardList[card], 'card');
            if (cardList[card].cardType == 'EXPEDITION') {
                cardList[card].image = cardList[card].cardType + '_' + cardList[card].anchor + '_' + cardList[card].cross + '_' + cardList[card].hut + imageFormat;
            } else {
                cardList[card].image = cardList[card].cardType + '_' + cardList[card].coin + '_' + cardList[card].influence + imageFormat;
            }
        }
        return cardList;
    }
};

angular.module('portRoyalApp.statsService', [])
    .service('statsService', statsService);