'use strict'

registerCtrl
    .controller('registerCtrl', function($scope, loginService, $location){

        $scope.userName;
        $scope.password;

        $scope.RegisterAction = RegisterAction;

        function RegisterAction(){
            var user = {
                login: $scope.userName,
                password: $scope.password
            };
            loginService.register(user);
            // login
        }
    });