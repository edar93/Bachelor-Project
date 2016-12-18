'use strict';

angular.module('portRoyalApp.view1', ['ngRoute'])
.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'components/templates/view1.html',
    controller: 'View1Ctrl'
  });
}])
.controller('View1Ctrl', function($http, backendGateway, loginService) {

    var promise = $http({
        method : 'GET',
        url : 'http://localhost:8080/port-royal/rest/testlist'
    })
    promise.then(function(response) {
        console.log('ok');
        console.log(response, 'response');
    }, function(response) {
        console.log('err');
        console.log(response, 'response');
    });
});