'use strict';
angular.module('portRoyalApp.loginCtrl', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/login', {
    templateUrl: 'components/templates/login_page.html',
    controller: 'loginCtrl'
  });
}])
.controller('loginCtrl', function(loginService, $scope) {

    $scope.testVariable = 'TEST';
    $scope.userName;
    $scope.password;

    loginService.login('user','user')
        .then(function(response){
            console.log(loginService.getUser(), 'user was logged');
        },function(response){
            console.log(loginService.getUser(), 'fail');
        });
});