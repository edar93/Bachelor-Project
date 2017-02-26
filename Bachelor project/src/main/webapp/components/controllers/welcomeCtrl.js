'use strict';

welcomeCtrl
    .controller('welcomeCtrl', function ($scope, loginService, locationService, gameService, globalChatService, baseInitService) {

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

        updateGamesForJoin();

        baseInitService.setVariables($scope);
        baseInitService.init();

        function joinGame(id) {
            gameService.joinGame(id)
                .then(function () {
                    locationService.goToGameCretion();
                });
        }

        function updateGamesForJoin() {
            gameService.getAllGamesToJoin()
                .then(function (data) {
                    $scope.gamesForJoin = data;
                }
            );
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
            //globalChatService.disconnect();
            locationService.goToLoginPage();
        }

        function goToRegistrationPage() {
            //globalChatService.disconnect();
            locationService.goToRegistrationPage();
        }
    }
);