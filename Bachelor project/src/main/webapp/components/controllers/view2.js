'use strict';

angular.module('portRoyalApp.view2', ['ngRoute'])
   .config(['$routeProvider', function($routeProvider) {
     $routeProvider.when('/view2', {
       templateUrl: 'components/templates/view2.html',
       controller: 'View2Ctrl'
     });
   }])
.controller('View2Ctrl', function($rootScope, $http, backendGateway, loginService) {

    loginService.login('user','user')
        .then(function(response){
            console.log(loginService.getUser(), 'user was logged');
        },function(response){
            console.log(loginService.getUser(), 'fail');
        });
});