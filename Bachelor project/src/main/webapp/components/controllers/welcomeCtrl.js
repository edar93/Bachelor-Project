'use strict';

welcomeCtrl
    .controller('welcomeCtrl', function ($scope, loginService, locationService, gameService, globalChatService, baseInitService) {

        globalChatService.initChat($scope);

        $scope.gamesForJoin = null;
        $scope.user = loginService.getUser();

        $scope.createGame = createGame;
        $scope.goToLoginPage = goToLoginPage;
        $scope.goToRegistrationPage = goToRegistrationPage;
        $scope.logout = logout;
        $scope.joinGame = joinGame;

        updateGamesForJoin();

        baseInitService.setVariables($scope);

        function joinGame(owner) {
            gameService.joinGame(owner)
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

        function logout() {
            console.log(loginService.logout(), 'loginService.logout()');
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