'use strict';

var gameCreationService = function (backendGateway, loginService, gameService, locationService) {

    this.initAndSetScope = initAndSetScope;

    var localScope;
    var owner = null;
    var user;

    function initAndSetScope(scope) {
        localScope = scope;
        scope.startGame = startGame;
        scope.leftGame = leftGame;
        scope.creator = false;
        scope.maxPlayers = 2;

        init();

        scope.$watch('maxPlayers', maxPlayersChange, true)
    }

    function leftGame() {
        backendGateway.post('LEFT_GAME')
            .then(locationService.goToWelcome);
    }

    function maxPlayersChange(newValue) {
        console.log('was callllllllllllllll');
        console.log(newValue);
        console.log('was callllllllllllllll');
    }

    function init() {
        var user = loginService.getUser();

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
        localScope.maxPlayers = data.maxPlayersCount;
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