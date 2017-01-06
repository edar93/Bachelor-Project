'use strict';

describe('portRoyalApp.loginService', function () {

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

    it('should return null when user is not set', function () {
        var user = loginService.getUser();

        expect(user).toEqual(null);
    });

    it('should call backendGateway with register URL and user', function () {
        var user = {
            login: 'MOCKED_USER',
            password: 'MOCKED_PASSWORD'
        };
        loginService.register(user);

        expect(backendGateway.post).toHaveBeenCalledWith('REGISTER_URL', user);
    });

    it('should set user to null and call backendGateway when logout function is called', function(){
        //TODO mock default user

        loginService.logout();

        expect(loginService.getUser()).toEqual(null);
        expect(backendGateway.post).toHaveBeenCalledWith('LOGOUT');
    });

    it('should login user and set user when login function is called', function(){
        var userName = 'MOCKED_USERNAME';
        var password = 'MOCKED_PASSWOED';
        var expectedConfig = {
            params: {
                username: userName,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        backendGateway.get.and.returnValue($q.resolve({data:'MOCKED_USER_FROM_BACKEND'}));
        backendGateway.post.and.returnValue($q.resolve());

        loginService.login(userName, password);

        $rootScope.$digest();
        expect(backendGateway.post).toHaveBeenCalledWith('LOGIN_URL', '', expectedConfig, true);
        expect(loginService.getUser()).toEqual('MOCKED_USER_FROM_BACKEND');
    });

    it('should set user value to null when login fail and user is anonymousUser', function(){
        var userName = 'MOCKED_USERNAME';
        var password = 'MOCKED_PASSWOED';
        var expectedConfig = {
            params: {
                username: userName,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        backendGateway.get.and.returnValue($q.resolve({data:'anonymousUser'}));
        backendGateway.post.and.returnValue($q.resolve());

        loginService.login(userName, password);

        $rootScope.$digest();
        expect(backendGateway.post).toHaveBeenCalledWith('LOGIN_URL', '', expectedConfig, true);
        expect(loginService.getUser()).toEqual(null);
    });

    it('should set user to null when getUser fail during login', function(){
        var userName = 'MOCKED_USERNAME';
        var password = 'MOCKED_PASSWOED';
        var expectedConfig = {
            params: {
                username: userName,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        backendGateway.get.and.returnValue($q.reject({data:'anonymousUser'}));
        backendGateway.post.and.returnValue($q.resolve());

        loginService.login(userName, password);

        $rootScope.$digest();
        expect(backendGateway.post).toHaveBeenCalledWith('LOGIN_URL', '', expectedConfig, true);
        expect(loginService.getUser()).toEqual(null);
    });

    it('should set user value to null if undefined data are returned', function(){
        var userName = 'MOCKED_USERNAME';
        var password = 'MOCKED_PASSWOED';
        var expectedConfig = {
            params: {
                username: userName,
                password: password
            },
            ignoreAuthModule: 'ignoreAuthModule'
        };

        backendGateway.get.and.returnValue($q.resolve(undefined));
        backendGateway.post.and.returnValue($q.resolve());

        loginService.login(userName, password);

        $rootScope.$digest();
        expect(backendGateway.post).toHaveBeenCalledWith('LOGIN_URL', '', expectedConfig, true);
        expect(loginService.getUser()).toEqual(null);
    });

});