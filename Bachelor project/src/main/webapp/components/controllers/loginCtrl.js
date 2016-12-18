'use strict';

loginCtrl
    .controller('loginCtrl', function (loginService, $scope) {

        $scope.userName;
        $scope.password;
        $scope.invalidCredentials = false;

        $scope.logIn = logIn;

        function logIn() {
            loginService.login($scope.userName, $scope.password)
                .then(function (response) {
                    console.log(loginService.getUser(), 'user was logged');
                    console.log(response);
                    $scope.invalidCredentials = false;
                }, function (response) {
                    console.log(loginService.getUser(), 'fail');
                    console.log(response);
                    $scope.invalidCredentials = true;
                });
        }


    });