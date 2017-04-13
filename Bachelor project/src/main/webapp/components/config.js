'use strict';

/* load modules */
var portRoyalApp = angular.module('portRoyalApp', [
    'ui.bootstrap',
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
    'portRoyalApp.statsService',
    'portRoyalApp.statsListService',
    'portRoyalApp.gameService',
    'portRoyalApp.gameCreationService',
    'portRoyalApp.gameCreationCtrl',
    'portRoyalApp.administrationService',
    'portRoyalApp.loginCtrl',
    'portRoyalApp.welcome',
    'portRoyalApp.administrationCtrl',
    'portRoyalApp.registerCtrl',
    'portRoyalApp.gameCtrl',
    'portRoyalApp.statsCtrl',
    'portRoyalApp.statsListCtrl'
]);

var loginCtrl = angular.module('portRoyalApp.loginCtrl', ['ngRoute']);
var welcomeCtrl = angular.module('portRoyalApp.welcome', ['ngRoute']);
var registerCtrl = angular.module('portRoyalApp.registerCtrl', ['ngRoute']);
var gameCreationCtrl = angular.module('portRoyalApp.gameCreationCtrl', ['ngRoute']);
var gameCtrl = angular.module('portRoyalApp.gameCtrl', ['ngRoute']);
var administrationCtrl = angular.module('portRoyalApp.administrationCtrl', ['ngRoute']);
var statsListCtrl = angular.module('portRoyalApp.statsListCtrl', ['ngRoute']);
var statsCtrl = angular.module('portRoyalApp.statsCtrl', ['ngRoute']);

/* routing */
portRoyalApp
    .config(['$locationProvider', '$routeProvider', function ($locationProvider, $routeProvider) {
        $locationProvider.hashPrefix('!');
        $routeProvider.otherwise({redirectTo: '/welcome'});
    }]);

statsListCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/statslist/:player/:page', {
            templateUrl: 'components/templates/stats_list.html',
            controller: 'statsListCtrl'
        });
    }]);

statsCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/stats/:game', {
            templateUrl: 'components/templates/stats.html',
            controller: 'statsCtrl'
        });
    }]);

loginCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/login', {
            templateUrl: 'components/templates/login_page.html',
            controller: 'loginCtrl'
        });
    }]);

administrationCtrl
    .config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/administration', {
            templateUrl: 'components/templates/administration.html',
            controller: 'administrationCtrl'
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

/* translation */
portRoyalApp
    .config(['$translateProvider', function ($translateProvider) {
        $translateProvider
            .translations('cz', dictionaryCZ)
            .preferredLanguage('cz');
    }]);