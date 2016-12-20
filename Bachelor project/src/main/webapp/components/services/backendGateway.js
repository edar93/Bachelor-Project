'use strict'
 var  backendGateway = function($http){

    this.post = post;
    this.get = get;

    var serverURL = 'http://localhost:8080/port-royal/';

    var URL = {
        LOGIN_URL: 'loginProcess',
        DICTIONARY_CZ: 'components/json/translation-cz.json',
        REGISTER_URL: 'accounts/register',
        GET_USER: 'accounts/getLoggedUserLogin',
        CREATE_NEW_GAME: 'game/cratenewgame'
    };

    function post(url, data, config, nonJsonResponce){
        if(nonJsonResponce){
            config = addNonJsonTransform(config);
        }
        return $http.post(translateUrl(url), data, config);
    }

    function get(url, config, nonJsonResponce){
        if(nonJsonResponce){
            config = addNonJsonTransform(config);
        }
        return $http.get(translateUrl(url), config);
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