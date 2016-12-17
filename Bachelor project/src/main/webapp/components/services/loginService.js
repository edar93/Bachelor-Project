'use strict'

 var  loginService = function(backendGateway, $q){

    this.login = login;
    this.getUser = getUser;

    var userName = null;

    function getUser(){
        return userName;
    }

    function login(userNameToLogin, password){
        var transform = function(data){
                return data;
            }
        var config = {
            params: {
            username: userNameToLogin,
            password: password
            },
            ignoreAuthModule: 'ignoreAuthModule',
            transformResponse: transform
        };
        return backendGateway.post('LOGIN_URL', '', config)
            .then(function(response){
                    userName = userNameToLogin;
                    return $q.resolve(response);
                },function(response){
                    userName = null;
                    return $q.reject(response);
                }
            )
    };

};

angular.module('portRoyalApp.loginService', [])
    .service('loginService', loginService);