'use strict';

/* load modules */
var portRoyalApp = angular.module('portRoyalApp', [
    'ngRoute',
    'rzModule',
    'pascalprecht.translate',
    'portRoyalApp.stompService',
    'portRoyalApp.backendGateway',
    'portRoyalApp.loginService',
    'portRoyalApp.locationService',
    'portRoyalApp.gameStatusService',
    'portRoyalApp.gameActionService',
    'portRoyalApp.welcomeService',
    'portRoyalApp.baseInitService',
    'portRoyalApp.globalChatService',
    'portRoyalApp.gameService',
    'portRoyalApp.gameCreationService',
    'portRoyalApp.gameCreationCtrl',
    'portRoyalApp.loginCtrl',
    'portRoyalApp.welcomeCtrl',
    'portRoyalApp.registerCtrl',
    'portRoyalApp.gameCtrl'
]);

var loginCtrl = angular.module('portRoyalApp.loginCtrl', ['ngRoute']);
var welcomeCtrl = angular.module('portRoyalApp.welcomeCtrl', ['ngRoute']);
var registerCtrl = angular.module('portRoyalApp.registerCtrl', ['ngRoute']);
var gameCreationCtrl = angular.module('portRoyalApp.gameCreationCtrl', ['ngRoute']);
var gameCtrl = angular.module('portRoyalApp.gameCtrl', ['ngRoute']);

/* routing */
loginCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'components/templates/login_page.html',
            controller: 'loginCtrl'
        });
    }]);

gameCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/game', {
            templateUrl: 'components/templates/game.html',
            controller: 'gameCtrl'
        });
    }]);


gameCreationCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/gamecreation', {
            templateUrl: 'components/templates/game_creation.html',
            controller: 'gameCreationCtrl'
        });
    }]);

registerCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/register', {
            templateUrl: 'components/templates/register.html',
            controller: 'registerCtrl'
        });
    }]);

welcomeCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/welcome', {
            templateUrl: 'components/templates/welcome.html',
            controller: 'welcomeCtrl'
        });
    }]);

portRoyalApp
    .config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider.otherwise({redirectTo: '/welcome'});
    }]);

/* translation */
portRoyalApp
    .config(['$translateProvider', function ($translateProvider) {
        $translateProvider
            .translations('cz', dictionaryCZ)
            .preferredLanguage('cz');
    }]);