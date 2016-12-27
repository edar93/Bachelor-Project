'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, gameService, loginService) {

        $scope.maxPlayers = maxPlayers;
        var maxPlayers = 1;

        init();

        function init(){
            gameService.getPlayersGame(loginService.getUser()).then(
                function(data){
                    console.log(data);
                    maxPlayers = data.maxPlayersCount;
                    $scope.maxPlayers = maxPlayers;
                }
            )
        }


    });