'use strict';

describe('test portRoyalApp.loginService', function () {

    var $q, $rootScope, loginService, backendGateway;

    beforeEach(function () {
        module('portRoyalApp.loginService');
        module({
            'backendGateway': jasmine.createSpyObj('backendGateway', ['get', 'post'])
        });

        inject(['$q', '$rootScope', 'loginService', 'backendGateway',
            function (_$q_, _$rootScope_, _loginService_, _backendGateway_) {
                $q = _$q_;
                $rootScope = _$rootScope_;
                loginService = _loginService_;
                backendGateway = _backendGateway_;
            }]);

    });

    it('should call backendGateway with register URL and user', function () {
        var user = {
            login: 'MOCKED_USER',
            password: 'MOCKED_PASSWORD'
        };
        loginService.register(user);

        expect(backendGateway.post).toHaveBeenCalledWith('REGISTER_URL', user);
    });

    it('should login user and return his login when login is called', function () {
        //prepare
        var loggedUser;
        var userName = 'MOCKED_USERNAME';
        var password = 'MOCKED_PASSWOED';
        var expectedConfig = {
            params: {
                username: userName,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        backendGateway.get.and.returnValue($q.resolve({data: 'MOCKED_USER_FROM_BACKEND'}));
        backendGateway.post.and.returnValue($q.resolve());
        //test
        loggedUser = loginService.login(userName, password);
        //validation
        $rootScope.$digest();
        expect(backendGateway.post).toHaveBeenCalledWith('LOGIN_URL', '', expectedConfig, false, true);
        expect(loggedUser).toEqual($q.resolve('MOCKED_USER_FROM_BACKEND'));
    });

    it('whould return rejected promise when login fail', function () {
        var loggedUser;
        var userName = 'MOCKED_USERNAME';
        var password = 'MOCKED_PASSWOED';
        var expectedConfig = {
            params: {
                username: userName,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        backendGateway.post.and.returnValue($q.reject());

        loggedUser = loginService.login(userName, password);

        $rootScope.$digest();
        expect(backendGateway.post).toHaveBeenCalledWith('LOGIN_URL', '', expectedConfig, false, true);
        expect(loggedUser).toEqual($q.reject());
    });

});