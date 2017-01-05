'use strict';

var loginService = function ($q, backendGateway) {

    this.login = login;
    this.register = register;
    this.getUser = getUser;
    this.logout = logout;

    var user;

    function logout() {
        user = null;
        return backendGateway.post('LOGOUT');
    }

    function getUser() {
        return user;
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

                return backendGateway.get('GET_USER', null, true)
                    .then(function (response) {
                        if (response.data == 'anonymousUser') {
                            user = null;
                            return $q.resolve(null);
                        }
                        user = response.data;
                        return $q.resolve(response.data);
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