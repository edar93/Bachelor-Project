'use strict';

var locationService = function ($location, $timeout, backendGateway) {

    this.goToLoginPage = goToLoginPage;
    this.goToRegistrationPage = goToRegistrationPage;
    this.goToGameCretion = goToGameCretion;
    this.goToWelcome = goToWelcome;
    this.goToGame = goToGame;
    this.startLocationCheck = startLocationCheck;

    var timeout;

    var paths = {
        welcome: '/welcome',
        gamecreation: '/gamecreation',
        game: '/game',
        login: '/login',
        register: '/register'
    };

    function startLocationCheck() {
        timeout = 20;
        $timeout(locationCheck, timeout);

    }

    function locationCheck() {
        backendGateway.get('GET_LOCATION')
            .then(function (responce) {
                var locationOnPage = responce.data;
                if(locationOnPage == 'GAME_CREATION'){
                    timeout = 600;
                }else {
                    timeout = 10000
                }

                if (locationOnPage == 'GAME_CREATION' && $location.path() != 'gamecreation') {
                    goToGameCretion();
                } else if (locationOnPage == 'GAME' && $location.path() != 'game') {
                    goToGame();
                }
                //else if (locationOnPage == 'FREE' && ($location.path() != 'game' || $location.path() != 'gamecreation')) {
                //    goToWelcome();
                //}
            });
        $timeout(locationCheck, timeout);
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