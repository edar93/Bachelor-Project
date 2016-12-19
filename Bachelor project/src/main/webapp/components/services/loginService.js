'use strict'

var loginService = function (backendGateway, $q) {

    this.login = login;
    this.register = register;
    this.getUser = getUser;

    function getUser() {
        return backendGateway.get('GET_USER', null, true)
            .then(function(response){
                console.log(response.data);
                if (response.data == 'anonymousUser'){
                    return null;
                }
                return response.data
            })
    }

    function login(userNameToLogin, password) {
        var transform = function (data) {
            return data;
        };
        var config = {
            params: {
                username: userNameToLogin,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule',
            transformResponse: transform
        };

        return backendGateway.post('LOGIN_URL', '', config)
            .then(function (response) {
                return $q.resolve(response);
            }, function (response) {
                return $q.reject(response);
            }
        )
    }

    function register(user){
         return backendGateway.post('REGISTER_URL', user)
    }

};

angular.module('portRoyalApp.loginService', [])
    .service('loginService', loginService);