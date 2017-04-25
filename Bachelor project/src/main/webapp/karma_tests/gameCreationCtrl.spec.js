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

                $scope = $rootScope.$new();
                gameCreationCtrl = $controller('gameCreationCtrl', {$scope: $scope});
            }]);

    });

    it('should initialize controller', function () {
        expect(gameCreationCtrl).toBeDefined();
    });

});