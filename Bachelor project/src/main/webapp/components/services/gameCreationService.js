'use strict';

var gameCreationService = function (backendGateway, loginService, gameService, locationService) {

    this.initAndSetScope = initAndSetScope;
    this.setSlider = setSlider;

    var localScope;
    var owner = null;
    var user;

    function initAndSetScope(scope) {
        localScope = scope;
        scope.startGame = startGame;
        scope.leftGame = leftGame;

        scope.creator = false;
        scope.maxPlayers = {count: 3};
        init();
        scope.$watch(getMaxPlayers, maxPlayersChange, true);
    }

    function kickPlayer() {

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
            .then(locationService.goToWelcome);
    }

    function maxPlayersChange(newValue) {
        console.log(newValue);
    }

    function init() {
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
                locationService.goToGame();
            });
    }

};
angular.module('portRoyalApp.gameCreationService', [])
    .service('gameCreationService', gameCreationService);