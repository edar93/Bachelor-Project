'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, gameService, loginService, locationService) {

        $scope.maxPlayers = maxPlayers;
        $scope.playersList = playersList;
        $scope.creator = false;

        $scope.startGame = startGame;

        var maxPlayers = 1;
        var owner = null;
        var playersList;

        init();

        function init() {

            loginService.getUser()
                .then(function (user) {
                    gameService.getPlayersGame(user)
                        .then(function (data) {
                            console.log(data);
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
                    )
                }
            );
        }

        function startGame() {
            locationService.goToGame();
        }
    });