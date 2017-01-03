'use strict';

gameCtrl
    .controller('gameCtrl', function ($scope, backendGateway, loginService, gameService) {



        loginService.getUser()
            .then(function (user) {
                console.log('user', user);
                gameService.getPlayersGame(user)
                    .then(function (data) {
                        console.log('data', data);
                        backendGateway.post('TEST', JSON.stringify(data)).then(
                            function(resoponse){
                                console.log(resoponse);
                            }
                        );
                    }
                )
            }
        );


});