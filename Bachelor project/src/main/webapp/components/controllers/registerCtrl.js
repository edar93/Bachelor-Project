'use strict'

registerCtrl
    .controller('registerCtrl', function ($scope, loginService, $location) {

        $scope.userName;
        $scope.password;
        $scope.invalidRegistration = false;

        $scope.RegisterAction = RegisterAction;

        function RegisterAction() {
            var user = {
                login: $scope.userName,
                password: $scope.password
            };

            loginService.register(user)
                .then(function (response) {
                    $scope.invalidRegistration = false;
                    loginService.login(user.login, user.password)
                        .then(function(){
                            $location.path("/welcome");
                        }
                    )
                }, function (response) {
                    $scope.invalidRegistration = true;
                }
            );
        }
    });