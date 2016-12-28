'use strict'

var loginService = function (backendGateway, $q) {

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
        if (user) {
            return $q.resolve(user);
        } else {
            return backendGateway.get('GET_USER', null, true)
                .then(function (response) {
                    if (response.data == 'anonymousUser') {
                        return $q.resolve(null);
                    }
                    user = response.data;
                    return $q.resolve(response.data);
                }
            )
        }
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

    function register(user) {
        return backendGateway.post('REGISTER_URL', user)
    }

};

angular.module('portRoyalApp.loginService', [])
    .service('loginService', loginService);