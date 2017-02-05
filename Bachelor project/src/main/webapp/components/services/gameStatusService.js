'use strict';

var gameStatusService = function ($timeout, backendGateway, gameService) {

        this.updateGame = updateGame;
        this.setScopeAndPlayer = setScopeAndPlayer;

        var imageFormat = '.jpg';
        var timeout = 800;

        var localUser, playersList, table, expeditions, playersCount, localPlayer, activePlayer, cardsToTake,
            gameScope, markedType, markedId, lastGameToShow, phase, playerOnTurn;

        function setScopeAndPlayer(scope, player) {
            gameScope = scope;
            localUser = player;
        }

        function updateGame(game) {
            console.log(game, 'game after global update');
            if (game) {
                lastGameToShow = game;
                showStates();
            } else {
                backendGateway.get('GET_MY_GAME').then(
                    function (resoponse) {
                        lastGameToShow = resoponse.data;
                        showStates();
                    });
            }
        }

        function showStates() {
            console.log(lastGameToShow, 'showstates.game');
            var phasesCount = lastGameToShow.semiStates.length;
            phase = 0;
            for (var i = 0; i < phasesCount; i++) {
                console.log(lastGameToShow.semiStates[i], 'game.semiStates[i]');
                $timeout(function () {
                    show(lastGameToShow.semiStates[phase], lastGameToShow.actionsToShows[phase])
                }, i * timeout);
            }
            $timeout(function () {
                show(lastGameToShow.currentGame, lastGameToShow.currentAction)
            }, phasesCount * timeout);
        }

        function show(cards, move) {
            console.log(cards, move, 'cards, move');
            prepair(cards, move);
            updateScope();
            phase++;
        }

        function prepair(game, action) {
            if (action) {
                markedType = action.marked;
                markedId = action.ids;
            } else {
                markedType = [];
                markedId = [];
            }

            console.log(game, 'game');
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
        }
    }
    ;

angular.module('portRoyalApp.gameStatusService', [])
    .service('gameStatusService', gameStatusService);