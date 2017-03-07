'use strict';

var locationService = function ($rootScope, $location, $timeout, backendGateway) {

    this.goToLoginPage = goToLoginPage;
    this.goToRegistrationPage = goToRegistrationPage;
    this.goToGameCretion = goToGameCretion;
    this.goToWelcome = goToWelcome;
    this.goToGame = goToGame;
    this.startLocationCheck = startLocationCheck;

    var timeout = 1000;
    var paths = {
        welcome: '/welcome',
        gamecreation: '/gamecreation',
        game: '/game',
        login: '/login',
        register: '/register'
    };

    function startLocationCheck() {
        $rootScope.startedCheckDate = new Date();
        console.log('time :', $rootScope.startedCheckDate.getTime());
        console.log('time :', $rootScope.startedCheckDate.toString());

        locationCheck();
        $timeout(locationCheck, timeout);
    }

    function locationCheck() {
        backendGateway.get('GET_LOCATION')
            .then(function (responce) {
                //if (locationOnPage == 'GAME_CREATION') {
                //    timeout = 1000;
                //} else {
                //    timeout = 1000;
                //}

                var locationOnPage = responce.data;
                if (locationOnPage == 'GAME_CREATION' && $location.path() != 'gamecreation') {
                    goToGameCretion();
                } else if (locationOnPage == 'GAME' && $location.path() != 'game') {
                    goToGame();
                }

                var newDate = new Date();
                if ($rootScope.startedCheckDate.getTime() + timeout <= newDate.getTime()){
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