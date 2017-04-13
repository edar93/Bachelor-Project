'use strict';

var backendGateway = function ($http, $q) {

    this.post = post;
    this.get = get;
    this.put = put;
    this.deleteHttp = deleteHttp;

    var serverURL = 'http://localhost:8090/port-royal/rest/';

    var URL = {
        LOGIN_URL: 'loginProcess',
        STATS_LIST_PAGES: 'stats/pagesCount',
        IS_ADMIN: 'accounts/isAdmin',
        LOGOUT: 'logout',
        USER_ADMINISTRATION: 'useradministration',
        GET_PAGES_COUNT: 'useradministration/pagesCount',
        STATS_PLAYER: 'stats',
        STATS_GAME: 'stats/game',
        REGISTER_URL: 'accounts/register',
        GET_USER: 'accounts/getLoggedUserLogin',
        GET_LOCATION: 'accounts/getmylocation',
        GAME_END: 'game/gameend',
        CREATE_NEW_GAME: 'game/cratenewgame',
        GET_PLAYERS_GAME: 'game/getplayersgame',
        GET_ALL_FREE_GAMES_IN_QUEUE: 'game/getallfreegamesinqueue',
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
        EVALUATE_ADMIRALS: 'play/applyadmiral'
    };

    function put(url, data, config, jsonRequest, nonJsonResponce, pathParam) {
        if (nonJsonResponce) {
            config = addNonJsonTransform(config);
        }
        if (jsonRequest) {
            data = JSON.stringify(data);
        }
        return $http.put(translateUrl(url, pathParam), data, config)
            .then(function (responese) {
                return $q.resolve(responese);
            }, function (responese) {
                console.log('put fail', url, data, config, jsonRequest, nonJsonResponce, responese, pathParam);
                return $q.reject(responese);
            }
        );
    }

    function post(url, data, config, jsonRequest, nonJsonResponce, pathParam) {
        if (nonJsonResponce) {
            config = addNonJsonTransform(config);
        }
        if (jsonRequest) {
            data = JSON.stringify(data);
        }
        return $http.post(translateUrl(url, pathParam), data, config)
            .then(function (responese) {
                return $q.resolve(responese);
            }, function (responese) {
                console.log('post fail', url, data, config, jsonRequest, nonJsonResponce, responese, pathParam);
                return $q.reject(responese);
            }
        );
    }

    function get(url, config, nonJsonResponce, pathParam) {
        if (nonJsonResponce) {
            config = addNonJsonTransform(config);
        }
        return $http.get(translateUrl(url, pathParam), config)
            .then(function (responese) {
                return $q.resolve(responese);
            }, function (responese) {
                console.log('get fail', url, config, nonJsonResponce, responese, pathParam);
                return $q.reject(responese);
            }
        );
    }

    function deleteHttp(url, pathParam) {
        return $http.delete(translateUrl(url, pathParam))
            .then(function (responese) {
                return $q.resolve(responese);
            }, function (responese) {
                console.log('delete fail', url, pathParam);
                return $q.reject(responese);
            }
        );
    }

    function translateUrl(url, pathParam) {
        if (URL[url]) {
            if (pathParam) {
                return serverURL + URL[url] + '/' + pathParam;
            }
            return serverURL + URL[url];
        }
        if (pathParam) {
            return serverURL + url + '/' + pathParam;
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