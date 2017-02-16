'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, gameService, loginService, locationService, baseInitService) {

        baseInitService.setVariables($scope);

        $scope.maxPlayers = maxPlayers;
        $scope.playersList = playersList;
        $scope.creator = false;

        $scope.startGame = startGame;

        var maxPlayers = 1;
        var owner = null;
        var playersList;
        var user;

        init();

        function init() {
            var user = loginService.getUser();

            loginService.getUser()
                .then(setUser)
                .then(gameService.getPlayersGame)
                .then(displayGame);
        }

        function setUser(player){
            user = player;
            return player
        }

        function displayGame(data){
            maxPlayers = data.maxPlayersCount;
            owner = data.owner;
            playersList = data.playersList;
            $scope.maxPlayers = maxPlayers;
            $scope.playersList = playersList;

            if (user === owner) {
                $scope.creator = true;
            } else {
                $scope.creator = false;
            }
        }

        function startGame() {
            gameService.startGame()
                .then(function () {
                    locationService.goToGame();
                });
        }
    });