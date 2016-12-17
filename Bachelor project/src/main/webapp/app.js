'use strict';

// Declare app level module which depends on views, and components
angular.module('portRoyalApp', [
  'ngRoute',
  'pascalprecht.translate',
  'portRoyalApp.view1',
  'portRoyalApp.view2',
  'portRoyalApp.backendGateway',
  'portRoyalApp.loginService',
  'portRoyalApp.loginCtrl'
]).
config(['$locationProvider', '$routeProvider', function($locationProvider, $routeProvider) {
  $locationProvider.hashPrefix('!');

  $routeProvider.otherwise({redirectTo: '/view1'});
}]);
