'use strict';
 var  backendGateway = function($http, $q){

    this.post = post;
    this.get = get;
    this.put = put;

    var serverURL = 'http://localhost:8080/port-royal/';

    var URL = {
        LOGIN_URL: 'loginProcess',
        LOGOUT: 'logout',
        REGISTER_URL: 'accounts/register',
        GET_USER: 'accounts/getLoggedUserLogin',
        CREATE_NEW_GAME: 'game/cratenewgame',
        GET_PLAYERS_GAME: 'game/getplayersgame',
        GET_ALL_GAMES_IN_QUEUE: 'game/getallgamesinqueue',
        JOIN_GAME: 'game/joingame'
    };

     function put(url, data, config, nonJsonResponce){
         if(nonJsonResponce){
             config = addNonJsonTransform(config);
         }
         return $http.put(translateUrl(url), data, config)
             .then(function(responese){
                 return $q.resolve(responese);
             },function(responese){
                 console.log('put fail', url, data, config, nonJsonResponce, responese);
                 return $q.reject(responese);
             }
         );
     }

    function post(url, data, config, nonJsonResponce){
        if(nonJsonResponce){
            config = addNonJsonTransform(config);
        }
        return $http.post(translateUrl(url), data, config)
            .then(function(responese){
                return $q.resolve(responese);
            },function(responese){
                console.log('post fail', url, data, config, nonJsonResponce, responese);
                return $q.reject(responese);
            }
        );
    }

    function get(url, config, nonJsonResponce){
        if(nonJsonResponce){
            config = addNonJsonTransform(config);
        }
        return $http.get(translateUrl(url), config)
            .then(function(responese){
                return $q.resolve(responese);
            },function(responese){
                console.log('get fail', url, config, nonJsonResponce, responese);
                return $q.reject(responese);
            }
        );
    }

     function translateUrl(url){
         if (URL[url]){ return serverURL + URL[url]; }
         return serverURL + url;
     }

     function addNonJsonTransform(config){
         var transform = function (data) {
             return data;
         };
         if (config){
             config.transformResponse = transform;
         }else{
             config = {transformResponse: transform};
         }
         return config;
     }
};

angular.module('portRoyalApp.backendGateway', [])
    .service('backendGateway', backendGateway);