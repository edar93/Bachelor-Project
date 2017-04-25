'use strict';

describe('test of locationService', function () {

    var $location, locationService, backendGateway, gameCreationService,
        paths;

    beforeEach(function () {
        module('portRoyalApp.locationService');
        module({
            '$location': jasmine.createSpyObj('$location', ['path']),
            'backendGateway': jasmine.createSpyObj('backendGateway', ['']),
            'gameCreationService': jasmine.createSpyObj('gameCreationService', ['']),
            'welcomeService': jasmine.createSpyObj('welcomeService', [''])
        });

        inject(['$location', 'locationService', 'backendGateway', 'gameCreationService', function (_$location_, _locationService_, _backendGateway_, _gameCreationService_) {
            $location = _$location_;
            locationService = _locationService_;
            backendGateway = _backendGateway_;
            gameCreationService = _gameCreationService_;

            paths = {
                welcome: '/welcome',
                gamecreation: '/gamecreation',
                game: '/game',
                login: '/login',
                register: '/register',
                stats: '/stats',
                playersStats: '/statslist'
            };
        }]);

    });

    it('should redirect to welcome page when goToWelcome is called', function () {
        locationService.goToWelcome();

        expect($location.path).toHaveBeenCalledWith(paths.welcome);
    });

    it('should redirect to game page when goToGame is called', function () {
        locationService.goToGame();

        expect($location.path).toHaveBeenCalledWith(paths.game);
    });

    it('should redirect to registration page when goToRegistrationPage is called', function () {
        locationService.goToRegistrationPage();

        expect($location.path).toHaveBeenCalledWith(paths.register);
    });

    it('should redirect to game cration page when goToGameCretion is called', function () {
        locationService.goToGameCretion();

        expect($location.path).toHaveBeenCalledWith(paths.gamecreation);
    });

    it('should redirect to login page when goToLoginPage is called', function () {
        locationService.goToLoginPage();

        expect($location.path).toHaveBeenCalledWith(paths.login);
    });
});
