'use strict';

var gameStatusService = function ($timeout, backendGateway) {

    this.updateGame = updateGame;
    this.setScopeAndPlayer = setScopeAndPlayer;

    var imageFormat = '.jpg';
    var timeout = 800;

    var localUser, playersList, table, expeditions, playersCount, localPlayer, activePlayer, cardsToTake,
        gameScope, markedType, markedId, lastGameToShow, phaseShow, playerOnTurn, admiralApplied, phase;

    function setScopeAndPlayer(scope, player) {
        gameScope = scope;
        gameScope.loadedGame = false;
        localUser = player;
    }

    function updateGame(game) {
        if (game) {
            lastGameToShow = game;
            showStates();
        } else {
            backendGateway.get('GET_MY_GAME').then(
                function (resoponse) {
                    lastGameToShow = resoponse.data;
                    showStates();
                    gameScope.loadedGame = true;
                }, function (resoponse) {
                    gameScope.loadedGame = true;
                });
        }
    }

    function showStates() {
        console.log(lastGameToShow.currentGame, 'current game');
        var phaseShowCount = lastGameToShow.semiStates.length;
        phaseShow = 0;
        for (var i = 0; i < phaseShowCount; i++) {
            $timeout(function () {
                show(lastGameToShow.semiStates[phaseShow], lastGameToShow.actionsToShows[phaseShow])
            }, i * timeout);
        }
        $timeout(function () {
            show(lastGameToShow.currentGame, lastGameToShow.currentAction)
        }, phaseShowCount * timeout);
    }

    function show(cards, move) {
        prepare(cards, move);
        updateScope();
        phaseShow++;
    }

    function prepare(game, action) {
        if (action) {
            markedType = action.marked;
            markedId = action.ids;
        } else {
            markedType = [];
            markedId = [];
        }
        if (game) {
            activePlayer = game.players[game.activePlayer].login;
            playerOnTurn = game.players[game.playerOnTurn].login;

            game = transformAddCards(game);
            for (var i = game.players.length - 1; i >= 0; i--) {
                if (game.players[i].login == localUser) {
                    localPlayer = game.players[i];
                    game.players.splice(i, 1);
                }
            }

            expeditions = game.expeditions;
            playersList = game.players;
            table = game.table;
            playersCount = game.playersCount;
            cardsToTake = game.cardsToTake;
            admiralApplied = game.admiralApplied;
            phase = game.phase;
        }
    }

    function transformAddCards(game) {
        for (var i = 0; i < game.players.length; i++) {
            for (var j = 0; j < game.players[i].cards.length; j++) {
                game.players[i].cards[j].image = transformCard(game.players[i].cards[j]);
            }
        }
        for (var i = 0; i < game.table.cards.length; i++) {
            game.table.cards[i].image = transformCard(game.table.cards[i]);
        }
        for (var i = 0; i < game.expeditions.cards.length; i++) {
            game.expeditions.cards[i].image = transformCard(game.expeditions.cards[i]);
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
        gameScope.markedType = markedType;
        gameScope.markedId = markedId;
        gameScope.table = table;
        gameScope.playersCount = playersCount;
        gameScope.playersList = playersList;
        gameScope.localPlayer = localPlayer;
        gameScope.expeditions = expeditions;
        gameScope.playerTakingCard = activePlayer;
        gameScope.playerOnTurn = playerOnTurn;
        gameScope.cardsToTake = cardsToTake;
        gameScope.localUser = localUser;
        gameScope.admiralApplied = admiralApplied;
        gameScope.phase = phase;
    }
};


angular.module('portRoyalApp.gameStatusService', [])
    .service('gameStatusService', gameStatusService);