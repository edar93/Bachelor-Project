'use strict';

welcomeCtrl
    .controller('welcomeCtrl', function ($scope, loginService, locationService, gameService, globalChatService, baseInitService, welcomeService) {

        baseInitService.setVariables($scope);
        baseInitService.init();

        globalChatService.initChat($scope);

        $scope.gamesForJoin = null;
        loginService.getUser()
            .then(function (user) {
                $scope.user = user;
            });

        $scope.createGame = createGame;
        $scope.goToLoginPage = goToLoginPage;
        $scope.goToRegistrationPage = goToRegistrationPage;
        $scope.joinGame = joinGame;

        welcomeService.init($scope);
        welcomeService.updateGamesForJoin();

        function joinGame(id) {
            gameService.joinGame(id)
                .then(function () {
                    locationService.goToGameCretion();
                });
        }

        function createGame() {
            gameService.createGame().then
            (function (response) {
                //globalChatService.disconnect();
                locationService.goToGameCretion();
            }, function (response) {
            });
        }

        function goToLoginPage() {
            locationService.goToLoginPage();
        }

        function goToRegistrationPage() {
            //globalChatService.disconnect();
            locationService.goToRegistrationPage();
        }
    }
);