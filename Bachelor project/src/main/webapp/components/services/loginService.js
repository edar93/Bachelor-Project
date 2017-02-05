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
        return backendGateway.get('GET_USER', null, true)
            .then(parseSuccessLoginData,
            parseFailLoginData
        );
    }

    function parseSuccessLoginData(response) {
        if (response == undefined || response.data == undefined) {
            return $q.resolve(null);
        }
        if (response.data == 'anonymousUser') {
            return $q.resolve(null);
        }
        return $q.resolve(response.data);
    }

    function parseFailLoginData(response) {
        return $q.reject(response.data);
    }

    function login(userNameToLogin, password) {
        var config = {
            params: {
                username: userNameToLogin,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        return backendGateway.post('LOGIN_URL', '', config, false, true)
            .then(function () {
                return backendGateway.get('GET_USER', null, true)
                    .then(parseSuccessLoginData
                    , parseFailLoginData
                )
            })
    }

    function register(user) {
        return backendGateway.post('REGISTER_URL', user);
    }

};
angular.module('portRoyalApp.loginService', [])
    .service('loginService', loginService);