'use strict';

var locationService = function ($location) {

    this.goToLoginPage = goToLoginPage;
    this.goToRegistrationPage = goToRegistrationPage;
    this.goToGameCretion = goToGameCretion;
    this.goToGame = goToGame;

    function goToGameCretion(){
        $location.path("/gamecreation");
    }

    function goToGame(){
        $location.path("/game");
    }

    function goToLoginPage(){
        $location.path("/login");
    }

    function goToRegistrationPage(){
        $location.path("/register");
    }
};

angular.module('portRoyalApp.locationService', [])
    .service('locationService', locationService);