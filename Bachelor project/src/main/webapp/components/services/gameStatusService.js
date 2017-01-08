'use strict';

var gameStatusService = function (backendGateway, gameService) {

    this.updateGame = updateGame;
    this.setScope = setScope;

    var imageFormat = '.jpg';
    var localUser;
    var playersList;
    var table;
    var playersCount;
    var localPlayer;
    var activePlayer;

    var gameScope;

    function setScope(scope) {
        gameScope = scope;
    }

    function updateGame(user) {
        localUser = user;
        gameService.getPlayersGame(user)
            .then(function (data) {
                backendGateway.post('TEST', data, undefined, true).then(
                    function (resoponse) {
                        var game = resoponse.data;
                        parseGameToService(game);
                        setToScope();
                        console.log(game);
                    }
                );
            }
        );
    }

    function parseGameToService(game) {
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
                game.players[i].cards[j] = transformCard(game.players[i].cards[j]);
            }
        }
        for (var i = 0; i < game.table.cards.length; i++) {
            game.table.cards[i] = transformCard(game.table.cards[i]);
        }
        return game;
    }

    function transformCard(card) {
        //TODO expeditions, taxes;
        return card.cardType + '_' + card.coin + '_' + card.influence + imageFormat;
    }

    function setToScope() {
        gameScope.table = table;
        gameScope.playersCount = playersCount;
        gameScope.playersList = playersList;
        gameScope.localPlayer = localPlayer;
    }
};

angular.module('portRoyalApp.gameStatusService', [])
    .service('gameStatusService', gameStatusService);