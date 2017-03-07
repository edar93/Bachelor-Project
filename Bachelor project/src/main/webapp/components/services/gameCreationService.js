'use strict';

var gameCreationService = function ($timeout, $location, backendGateway, loginService, gameService) {

    this.initAndSetScope = initAndSetScope;
    this.setSlider = setSlider;
    this.updateGame = updateGame;

    var updateGameInterval = 1000;
    var localScope;
    var owner = null;
    var user;

    function initAndSetScope(scope) {
        localScope = scope;
        scope.startGame = startGame;
        scope.leftGame = leftGame;

        scope.creator = false;
        scope.maxPlayers = {count: 3};
        updateGame();
        scope.$watch(getMaxPlayers, maxPlayersChange, true);
    }

    function kickPlayer() {
        //TODO
    }

    function getMaxPlayers(scope) {
        return localScope.maxPlayers.count;
    }

    function setSlider(scope) {
        scope.slider = {
            options: {
                floor: 2,
                ceil: 5,
                showTicks: true
            }
        };
    }

    function leftGame() {
        backendGateway.post('LEFT_GAME')
            .then(function(){
                $location.path('/welcome');
            });
    }

    function maxPlayersChange(newValue) {
        //TODO
        console.log(newValue);
    }

    function updateGame() {
        loginService.getUser()
            .then(setUser)
            .then(gameService.getPlayersGame)
            .then(displayGame);
    }

    function setUser(player) {
        user = player;
        return player
    }

    function displayGame(data) {
        owner = data.owner;
        localScope.maxPlayers.count = data.maxPlayersCount;
        localScope.playersList = data.playersList;

        if (user === owner) {
            localScope.creator = true;
        } else {
            localScope.creator = false;
        }
    }

    function startGame() {
        gameService.startGame()
            .then(function () {
                $location.path('/game');
            });
    }

};
angular.module('portRoyalApp.gameCreationService', [])
    .service('gameCreationService', gameCreationService);