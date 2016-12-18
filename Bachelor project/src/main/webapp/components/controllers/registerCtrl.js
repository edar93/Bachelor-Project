'use strict'

registerCtrl
    .controller('registerCtrl', function($scope, loginService, $location){

        $scope.user = loginService.getUser();

        $scope.RegisterAction = RegisterAction;

        function RegisterAction(){
            // rest to register
            // login
        }
    });