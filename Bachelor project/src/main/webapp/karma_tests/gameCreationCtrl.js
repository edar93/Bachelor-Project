'use strict';

describe('portRoyalApp.gameCreationCtrl', function () {

    var $controller, $scope, $rootScope, $q,
        gameCreationCtrl;

    beforeEach(function () {
        module('portRoyalApp.gameCreationCtrl');
        module({
            'baseInitService': jasmine.createSpyObj('baseInitService', ['setVariables', 'init']),
            'gameCreationService': jasmine.createSpyObj('gameCreationService', ['initAndSetScope', 'setSlider'])
        });

        inject(['$controller', '$rootScope', '$q', 'baseInitService', 'gameCreationService',
            function (_$controller_, _$rootScope_, _$q_, _baseInitService_, _gameCreationService_) {
                $controller = _$controller_;
                $rootScope = _$rootScope_;
                $q = _$q_;
                baseInitService = _baseInitService_;
                gameCreationService = _gameCreationService_;

                //gameService.getAllGamesToJoin.and.returnValue($q.resolve('MOCKED_VALUE'));
                //loginService.getUser.and.returnValue($q.resolve('MOCKED_USER'));

                $scope = $rootScope.$new();
                welcomeCtrl = $controller('gameCreationCtrl', {$scope: $scope});
            }]);

    });

    it('controller is inicialized', function () {
        expect(welcomeCtrl).toBeDefined();
    });

});