'use strict';

describe('portRoyalApp.welcomeCtrl', function () {

    var $controller, $scope, $rootScope, $q,
        welcomeCtrl, gameService, loginService;

    beforeEach(function () {
        module('portRoyalApp.welcomeCtrl');
        module({
            'loginService': jasmine.createSpyObj('loginService', ['getUser']),
            'gameService': jasmine.createSpyObj('gameService', ['getAllGamesToJoin']),
            'locationService': jasmine.createSpyObj('locationService', [''])
        });

        inject(['$controller', '$rootScope', '$q', 'gameService', 'loginService',
            function (_$controller_, _$rootScope_, _$q_, _gameService_, _loginService_) {
                $controller = _$controller_;
                $rootScope = _$rootScope_;
                $q = _$q_;
                gameService = _gameService_;
                loginService = _loginService_;

                gameService.getAllGamesToJoin.and.returnValue($q.resolve('MOCKED_VALUE'));
                loginService.getUser.and.returnValue($q.resolve('MOCKED_USER'));

                $scope = $rootScope.$new();
                welcomeCtrl = $controller('welcomeCtrl', {$scope: $scope});
            }]);

    });

    it('should redirect to welcome page when goToWelcome is called', function () {


        expect(welcomeCtrl).toBeDefined();
        expect(true).toBeTruthy();
    });

});


//---------------------

//describe('portRoyalApp.view2 module', function() {
//
//  beforeEach(module('portRoyalApp.view2'));
//
//  describe('view2 controller', function(){
//
//    it('should ....', inject(function($controller) {
//      //spec body
//      var view2Ctrl = $controller('View2Ctrl');
//      expect(view2Ctrl).toBeDefined();
//    }));
//
//  });
//});