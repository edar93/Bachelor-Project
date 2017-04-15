'use strict';

welcomeCtrl
    .controller('welcomeCtrl', function ($scope, loginService, locationService, gameService, globalChatService, baseInitService, welcomeService) {


        // test for theoretical part
        //$scope.connect = connect;
        //$scope.subscribe = subscribe;
        //$scope.send = send;
        //
        //var client;
        //
        //function connect() {
        //    var socket = new SockJS('/port-royal/old-backend/chat');
        //    client = Stomp.over(socket);
        //    client.connect();
        //};
        //
        //function subscribe() {
        //    client.subscribe("/messages", function (message) {
        //        var body = JSON.parse(message.body);
        //        console.log('message was recived', body);
        //    });
        //};
        //
        //function send () {
        //    client.send("/port-royal/old-backend/sendMessage", {}, JSON.stringify(
        //        {'text': 'test message',
        //            'author': 'Honza Cech'}
        //    ));
        //};


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