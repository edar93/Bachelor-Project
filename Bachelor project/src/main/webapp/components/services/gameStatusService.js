'use strict';

var gameStatusService = function (backendGateway, gameService) {

    this.updateGame = updateGame;
    this.setScopeAndPlayer = setScopeAndPlayer;

    var imageFormat = '.jpg';
    var localUser;
    var playersList;
    var table;
    var playersCount;
    var localPlayer;
    var activePlayer;

    var gameScope;

    function setScopeAndPlayer(scope, player) {
        gameScope = scope;
        localUser = player;
    }

    function updateGame(game) {
        if (game) {
            parseGameToService(game);
            updateScope();
        } else {
            backendGateway.get('GET_MY_GAME').then(
                function (resoponse) {
                    parseGameToService(resoponse.data);
                    updateScope();
                });
        }
    }

    function parseGameToService(gameManipulator) {
        var game = gameManipulator.currentGame;
        activePlayer = game.players[game.playerOnTurn].login;

        game = transformAddCards(game);
        for (var i = game.players.length - 1; i >= 0; i--) {
            if (game.players[i].login == localUser) {
                localPlayer = game.players[i];
                game.players.splice(i, 1);
            }
        }

        playersList = game.players;
        table = game.table;
        playersCount = game.playersCount;
    }

    function transformAddCards(game) {
        //TODO discovered expeditions
        for (var i = 0; i < game.players.length; i++) {
            for (var j = 0; j < game.players[i].cards.length; j++) {
                game.players[i].cards[j].image = transformCard(game.players[i].cards[j]);
            }
        }
        for (var i = 0; i < game.table.cards.length; i++) {
            game.table.cards[i].image = transformCard(game.table.cards[i]);
        }
        return game;
    }

    function transformCard(card) {
        if (card.cardType == 'TAX_INFLUENCE' || card.cardType == 'TAX_SWORDS') {
            return card.cardType + imageFormat;
        }
        if (card.cardType == 'EXPEDITION') {
            return card.cardType + '_' + card.anchor + '_' + card.cross + '_' + card.hut + imageFormat;
        }
        return card.cardType + '_' + card.coin + '_' + card.influence + imageFormat;
    }

    function updateScope() {
        gameScope.table = table;
        gameScope.playersCount = playersCount;
        gameScope.playersList = playersList;
        gameScope.localPlayer = localPlayer;
    }
};

angular.module('portRoyalApp.gameStatusService', [])
    .service('gameStatusService', gameStatusService);