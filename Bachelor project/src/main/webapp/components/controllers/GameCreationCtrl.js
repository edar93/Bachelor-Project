'use strict';

gameCreationCtrl
    .controller('gameCreationCtrl', function ($scope, gameService) {

        $scope.maxPlayers = maxPlayers;
        var maxPlayers = 1;


        console.log(gameService.getBaseGameStatus(), 'gameService.getBaseGameStatus()');

        init();

        function init(){
            gameService.getBaseGameStatus().then(
                function(response){
                    maxPlayers = response.data.maxPlayersCount;
                    $scope.maxPlayers = maxPlayers;
                }
            )
        }


    });