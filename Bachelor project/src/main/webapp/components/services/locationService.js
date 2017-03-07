'use strict';

var locationService = function ($rootScope, $route, $location, $timeout, backendGateway, gameCreationService, welcomeService) {

    this.goToLoginPage = goToLoginPage;
    this.goToRegistrationPage = goToRegistrationPage;
    this.goToGameCretion = goToGameCretion;
    this.goToWelcome = goToWelcome;
    this.goToGame = goToGame;
    this.startLocationCheck = startLocationCheck;

    var timeout = 1000;
    //gameCreationService - use url too (without wariable)
    // to avoid circular dependency
    var paths = {
        welcome: '/welcome',
        gamecreation: '/gamecreation',
        game: '/game',
        login: '/login',
        register: '/register'
    };

    function startLocationCheck() {
        $rootScope.startedCheckDate = new Date();
        locationCheck();
        $timeout(locationCheck, timeout);
    }

    function locationCheck() {
        backendGateway.get('GET_LOCATION')
            .then(function (responce) {
                if ($location.path() == '/gamecreation') {
                    gameCreationService.updateGame();
                }
                if ($location.path() == '/welcome') {
                    welcomeService.updateGamesForJoin();
                }

                var locationOnPage = responce.data;
                if (locationOnPage == 'GAME_CREATION' && $location.path() != '/gamecreation') {
                    goToGameCretion();
                } else if (locationOnPage == 'GAME' && $location.path() != '/game') {
                    goToGame();
                }

                var newDate = new Date();
                if ($rootScope.startedCheckDate.getTime() + timeout <= newDate.getTime()) {
                    $rootScope.startedCheckDate = newDate;
                    $timeout(locationCheck, timeout);
                }
            });
    }

    function goToWelcome() {
        $location.path(paths.welcome);
    }

    function goToGameCretion() {
        $location.path(paths.gamecreation);
    }

    function goToGame() {
        $location.path(paths.game);
    }

    function goToLoginPage() {
        $location.path(paths.login);
    }

    function goToRegistrationPage() {
        $location.path(paths.register);
    }
};

angular.module('portRoyalApp.locationService', [])
    .service('locationService', locationService);