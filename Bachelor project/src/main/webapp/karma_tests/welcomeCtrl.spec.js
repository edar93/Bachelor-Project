'use strict';

describe('portRoyalApp.welcome', function () {

    var $controller, $scope, $rootScope, $q,
        welcomeCtrl;

    beforeEach(function () {
        module('portRoyalApp.welcome');
        module({
            'baseInitService': jasmine.createSpyObj('baseInitService', ['setVariables', 'init']),
            'loginService': jasmine.createSpyObj('loginService', ['getUser']),
            'gameService': jasmine.createSpyObj('gameService', ['joinGame', 'createGame']),
            'globalChatService': jasmine.createSpyObj('globalChatService', ['initChat']),
            'welcomeService': jasmine.createSpyObj('welcomeService', ['init', 'updateGamesForJoin']),
            'locationService': jasmine.createSpyObj('locationService', ['goToGameCretion', 'goToGameCretion', 'goToLoginPage', 'goToRegistrationPage'])
        });

        inject(['$controller', '$rootScope', '$q', 'baseInitService', 'loginService', 'locationService',
            function (_$controller_, _$rootScope_, _$q_, _baseInitService_, _loginService_, _locationService_) {
                $controller = _$controller_;
                $rootScope = _$rootScope_;
                $q = _$q_;
                baseInitService = _baseInitService_;
                loginService = _loginService_;
                locationService = _locationService_;
                //prepare mock
                loginService.getUser.and.returnValue($q.resolve('MOCKED_USER'));
                $scope = $rootScope.$new();
                welcomeCtrl = $controller('welcomeCtrl', {$scope: $scope});
            }]);

    });

    it('should initialize controller', function () {
        expect(welcomeCtrl).toBeDefined();
    });

    it('should get user from loginService and set him to scope when is controller initialized', function () {
        $rootScope.$digest();
        expect($scope.user).toEqual('MOCKED_USER');
    });

});