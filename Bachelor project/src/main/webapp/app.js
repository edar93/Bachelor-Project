'use strict';

// Declare app level module which depends on views, and components
var portRoyalApp = angular.module('portRoyalApp', [
  'ngRoute',
  'pascalprecht.translate',
  'portRoyalApp.view1',
  'portRoyalApp.view2',
  'portRoyalApp.backendGateway',
  'portRoyalApp.loginService',
  'portRoyalApp.loginCtrl'
])
.config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');
  $routeProvider.otherwise({redirectTo: '/view1'});
}])

portRoyalApp.config(['$translateProvider', function ($translateProvider) {
  console.log('log');
  $translateProvider
    .translations('en', {   FOO: 'hovinko',
                            TEST: 'translated test'})
    .preferredLanguage('en');
}]);