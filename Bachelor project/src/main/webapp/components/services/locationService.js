'use strict';

var locationService = function ($rootScope, $location, $timeout, backendGateway, gameCreationService, welcomeService) {

    this.goToLoginPage = goToLoginPage;
    this.goToRegistrationPage = goToRegistrationPage;
    this.goToGameCretion = goToGameCretion;
    this.goToWelcome = goToWelcome;
    this.goToGame = goToGame;
    this.startLocationCheck = startLocationCheck;
    this.showStatsRecord = showStatsRecord;
    this.showPlayersStats = showPlayersStats;
    this.tellScopeLocationStatus = tellScopeLocationStatus;

    var timeout = 2200;
    var localScope;

    //gameCreationService - use url too (without wariable)
    // to avoid circular dependency
    var paths = {
        welcome: '/welcome',
        gamecreation: '/gamecreation',
        game: '/game',
        login: '/login',
        register: '/register',
        stats: '/stats',
        playersStats: '/statslist'
    };

    function tellScopeLocationStatus(scope) {
        localScope = scope;
    }

    function startLocationCheck() {
        console.log('starting loc check');
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
                localScope.gameLocation = responce.data;
                if (locationOnPage == 'GAME_CREATION' && $location.path() != '/gamecreation') {
                    goToGameCretion();
                } else if ((locationOnPage == 'GAME' || locationOnPage == 'GAME_OVER') && $location.path() != '/game') {
                    goToGame();
                } else if (locationOnPage == 'FREE' && ($location.path() == '/game' || $location.path() == '/gamecreation')) {
                    goToWelcome();
                } else if (locationOnPage == 'UNLOGGED' && ($location.path() == '/game' || $location.path() == '/gamecreation' || $location.path() == '/administration')) {
                    goToWelcome();
                }

                var newDate = new Date();
                if ($rootScope.startedCheckDate.getTime() + timeout <= newDate.getTime()) {
                    $rootScope.startedCheckDate = newDate;
                    $timeout(locationCheck, timeout);
                }

                console.log(locationOnPage, 'location check done');
            });
    }

    function showPlayersStats(login, page) {
        if (!page) {
            page = 1;
        }
        $location.path(paths.playersStats + '/' + login + '/' + page);
    }

    function showStatsRecord(gameId) {
        $location.path(paths.stats + '/' + gameId);
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