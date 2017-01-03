'use strict';

welcomeCtrl
    .controller('welcomeCtrl', function ($scope, loginService, locationService, gameService) {

        $scope.user = null;
        $scope.gamesForJoin = null;

        $scope.createGame = createGame;
        $scope.goToLoginPage = goToLoginPage;
        $scope.goToRegistrationPage = goToRegistrationPage;
        $scope.logout = logout;
        $scope.joinGame = joinGame;

        updateGamesForJoin();

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
            console.log('createGame was called');
            gameService.createGame().then
            (function (response) {
                console.log(response.data, 'game was created');
            }, function (response) {
                console.log(response.data, 'game creation fail');
            });

            locationService.goToGameCretion();
        }

        loginService.getUser().then(
            function (data) {
                $scope.user = data
            }
        );

        function goToLoginPage() {
            locationService.goToLoginPage();
        }

        function goToRegistrationPage() {
            locationService.goToRegistrationPage();
        }
    }
);