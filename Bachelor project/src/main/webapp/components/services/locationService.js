'use strict';

var locationService = function ($location) {

    this.goToLoginPage = goToLoginPage;
    this.goToRegistrationPage = goToRegistrationPage;
    this.goToGameCretion = goToGameCretion;

    function goToGameCretion(){
        $location.path("/gamecreation");
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