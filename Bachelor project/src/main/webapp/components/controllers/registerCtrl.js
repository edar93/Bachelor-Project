'use strict';

registerCtrl
    .controller('registerCtrl', function ($scope, loginService, locationService) {

        $scope.userName;
        $scope.password;
        $scope.invalidRegistration = false;

        $scope.RegisterAction = RegisterAction;

        function RegisterAction() {
            var user = {
                login: $scope.userName,
                password: $scope.password
            };
            console.log('RegisterAction was called');
            loginService.register(user)
                .then(function (response) {
                    console.log('RegisterAction-then was called');
                    $scope.invalidRegistration = false;
                    loginService.login(user.login, user.password)
                        .then(function () {
                            locationService.goToWelcome();
                        }
                    )
                }, function (response) {
                    $scope.invalidRegistration = true;
                }
            );
        }
    });