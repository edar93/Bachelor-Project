'use strict';

describe('test of service administrationService', function () {

    var $q, $rootScope, $scope, administrationService, locationService, backendGateway;

    var mockeUser = 'MOCKED_USER';
    var mockedUserList = {
        data: [{
            userRoleLis: [{role: 'ROLE_ADMINISTRATOR', login: mockeUser},
                {role: 'ROLE_USER', login: mockeUser}],
            login: mockeUser,
            admin: true
        }]
    };

    beforeEach(function () {
        module('portRoyalApp.administrationService');
        module({
            'backendGateway': jasmine.createSpyObj('backendGateway', ['get', 'put']),
            'locationService': jasmine.createSpyObj('locationService', [''])
        });

        inject(['administrationService', '$q', '$rootScope', 'locationService', 'backendGateway',
            function (_administrationService_, _$q_, _$rootScope_, _locationService_, _backendGateway_) {
                administrationService = _administrationService_;
                $q = _$q_;
                $rootScope = _$rootScope_;
                locationService = _locationService_;
                backendGateway = _backendGateway_;
                //prepare mock
                backendGateway.put.and.returnValue($q.resolve());
                backendGateway.get.and.callFake(function (input) {
                    if (input === 'GET_PAGES_COUNT') {
                        return $q.resolve({data: 4});
                    } else if (input === 'USER_ADMINISTRATION') {
                        return $q.resolve({data: mockedUserList})
                    }
                });
                // service initialization
                $scope = $rootScope.$new();
                administrationService.initPage($scope);
                $rootScope.$digest();
            }]);
    });

    it('should set administration variables to scope when is service initialized', function () {
        expect($scope.pagesCount).toEqual([0, 1, 2, 3]);
        expect($scope.usersList).toEqual(mockedUserList);
    });

    it('should call backend gateway when resetPassword function is called', function () {
        //prepare
        var playersLogin = 'MOCKED_USER';
        //test
        $scope.resetPassword(playersLogin);
        $rootScope.$digest();
        //validation
        expect(backendGateway.put).toHaveBeenCalledWith('USER_ADMINISTRATION', undefined, undefined, true, false, playersLogin + '/RESET_PASSWORD');
        expect(backendGateway.get).toHaveBeenCalledWith('USER_ADMINISTRATION', false, false, 1);
        expect($scope.usersList).toEqual(mockedUserList);

    });

    it('should call backend gateway when resetPassword function is called', function () {
        //prepare
        var playersLogin = 'MOCKED_USER';
        //test
        $scope.promoteToAdmin(playersLogin);
        $rootScope.$digest();
        //validation
        expect(backendGateway.put).toHaveBeenCalledWith('USER_ADMINISTRATION', undefined, undefined, true, false, playersLogin + '/GRANT_ADMIN_ROLE');
        expect(backendGateway.get).toHaveBeenCalledWith('USER_ADMINISTRATION', false, false, 1);
        expect($scope.usersList).toEqual(mockedUserList);
    });

});