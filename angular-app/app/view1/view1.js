'use strict';

angular.module('myApp.view1', ['ngRoute'])

.config(['$routeProvider', function($routeProvider) {
  $routeProvider.when('/view1', {
    templateUrl: 'view1/view1.html',
    controller: 'View1Ctrl'
  });
}])

.controller('View1Ctrl', function($scope, $http) {
    var promise = $http({
        method : 'GET',
//        headers: { Accept: 'application/json' },
        url : 'http://localhost:8080/port-royal/rest/testlist',

    })

    console.log(promise, 'primise');
    promise.then(function(response) {
        console.log(response, 'response');
        $scope.testVariable = response;
    }, function(response) {
        $scope.testVariable = 'error';
        console.log(response, 'response');
    });





    $scope.testVariable = 'ahoj :)';
    console.log('log test');

});