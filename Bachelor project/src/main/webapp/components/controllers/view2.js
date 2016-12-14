'use strict';

angular.module('portRoyalApp.view2', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view2', {
    templateUrl: 'components/templates/view2.html',
    controller: 'View2Ctrl'
  });
}])

.controller('View2Ctrl', function($rootScope, $http) {
    var transform = function(data){
            return data;
        }
   var config = {
    params: {
     username: 'user',
     password: 'user',
    },
    ignoreAuthModule: 'ignoreAuthModule',
    transformResponse: transform
   };
   $http.post('loginProcess', '', config)
    .success(function(data, status, headers, config) {
    console.log('prihlaseni');
    console.log(data, 'data');
    console.log(status, 'status');
    console.log(headers, 'headers');
    console.log(config, 'config');
    }).error(function(data, status, headers, config) {

    });
});

/*myApp.service('AuthSharedService', function($rootScope, $http, authService, Session) {
 return {
  login: function(userName, password, rememberMe) {

  }
 };*/
