'use strict'
 var  backendGateway = function($http){

    this.post = post;
    this.get = get;

    var serverURL = 'http://localhost:8080/port-royal/';

    var URL = {
        LOGIN_URL: 'loginProcess',
        DICTIONARY_CZ: 'components/json/translation-cz.json'
    };

    function translateUrl(url){
        if (URL[url]){ return serverURL + URL[url]; }
        return serverURL + url;
    }

    function post(url, data, config){
        return $http.post(translateUrl(url), data, config);
    }

    function get(url, data, config){
        return $http.get(translateUrl(url), data, config);
    }
};

angular.module('portRoyalApp.backendGateway', [])
    .service('backendGateway', backendGateway);