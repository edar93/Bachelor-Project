'use strict';

loginCtrl
    .controller('loginCtrl', function ($scope, $location, loginService) {

        console.log('loginCtrl');

        $scope.userName;
        $scope.password;
        $scope.invalidCredentials = false;

        $scope.logIn = logIn;

        function logIn() {
            loginService.login($scope.userName, $scope.password)
                .then(function (response) {
                    $scope.invalidCredentials = false;
                    $location.path("/welcome");
                }, function (response) {
                    $scope.invalidCredentials = true;
                }
            );
        }
    });