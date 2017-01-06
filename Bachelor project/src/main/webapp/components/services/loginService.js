'use strict';

var loginService = function ($q, backendGateway) {

    this.login = login;
    this.register = register;
    this.getUser = getUser;
    this.logout = logout;

    var user = null;

    function logout() {
        user = null;
        return backendGateway.post('LOGOUT');
    }

    function getUser() {
        return user;
    }

    function login(userNameToLogin, password) {
        var config = {
            params: {
                username: userNameToLogin,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        return backendGateway.post('LOGIN_URL', '', config, true)
            .then(function (response) {
                return backendGateway.get('GET_USER', null, true)
                    .then(function (response) {
                        if(response == undefined || response.data == undefined){
                            user = null;
                            return $q.resolve(undefined);
                        }
                        if (response.data == 'anonymousUser') {
                            user = null;
                            return $q.resolve(null);
                        }
                        user = response.data;
                        return $q.resolve(response.data);
                    },function(response){
                        user = null;
                    }
                )
            }
        )
    }

    function register(user) {
        return backendGateway.post('REGISTER_URL', user)
    }

};

angular.module('portRoyalApp.loginService', [])
    .service('loginService', loginService);