'use strict';

var backendGateway = function ($http, $q) {

    this.post = post;
    this.get = get;
    this.put = put;

    var serverURL = 'http://localhost:8090/port-royal/';

    var URL = {
        LOGIN_URL: 'loginProcess',
        LOGOUT: 'logout',
        REGISTER_URL: 'accounts/register',
        GET_USER: 'accounts/getLoggedUserLogin',
        CREATE_NEW_GAME: 'game/cratenewgame',
        GET_PLAYERS_GAME: 'game/getplayersgame',
        GET_ALL_GAMES_IN_QUEUE: 'game/getallgamesinqueue',
        KICK: 'game/kick',
        NEW_MAX_PLAYERS_COUNT: 'game/changeplayersmaxcount',
        LEFT_GAME: 'game/leftgame',
        JOIN_GAME: 'game/joingame',
        TEST: 'play/gettestgame',
        START_GAME: 'play/startGame',
        FACE_CARD: 'play/facecard',
        PICK_CARD: 'play/pickcard',
        PICK_EXPEDITION: 'play/pickexpedition',
        SKIP_ACTION: 'play/skipaction',
        GET_MY_GAME: 'play/getMyGame',
        GET_LOCATION: 'play/getmylocation',
        EVALUATE_ADMIRALS: 'play/applyadmiral'
    };

    function put(url, data, config, jsonRequest, nonJsonResponce) {
        if (nonJsonResponce) {
            config = addNonJsonTransform(config);
        }
        if (jsonRequest) {
            data = JSON.stringify(data);
        }
        return $http.put(translateUrl(url), data, config)
            .then(function (responese) {
                return $q.resolve(responese);
            }, function (responese) {
                console.log('put fail', url, data, config, jsonRequest, nonJsonResponce, responese);
                return $q.reject(responese);
            }
        );
    }

    function post(url, data, config, jsonRequest, nonJsonResponce) {
        if (nonJsonResponce) {
            config = addNonJsonTransform(config);
        }
        if (jsonRequest) {
            data = JSON.stringify(data);
        }
        return $http.post(translateUrl(url), data, config)
            .then(function (responese) {
                return $q.resolve(responese);
            }, function (responese) {
                console.log('post fail', url, data, config, jsonRequest, nonJsonResponce, responese);
                return $q.reject(responese);
            }
        );
    }

    function get(url, config, nonJsonResponce) {
        //TODO add jsonRequest
        if (nonJsonResponce) {
            config = addNonJsonTransform(config);
        }
        return $http.get(translateUrl(url), config)
            .then(function (responese) {
                return $q.resolve(responese);
            }, function (responese) {
                console.log('get fail', url, config, nonJsonResponce, responese);
                return $q.reject(responese);
            }
        );
    }

    function translateUrl(url) {
        if (URL[url]) {
            return serverURL + URL[url];
        }
        return serverURL + url;
    }

    function addNonJsonTransform(config) {
        var transform = function (data) {
            return data;
        };
        if (config) {
            config.transformResponse = transform;
        } else {
            config = {transformResponse: transform};
        }
        return config;
    }
};
angular.module('portRoyalApp.backendGateway', [])
    .service('backendGateway', backendGateway);