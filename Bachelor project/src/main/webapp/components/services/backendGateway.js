'use strict'
 var  backendGateway = function($http){

    this.sayHello = sayHello;
    this.post = post;

    var URL = {
        LOGIN_URL: 'loginProcess'
    }

    function sayHello(){
        console.log('hello from backendGateway');
    };

    function translateUrl(url){
        if (URL[url]){ return URL[url]; }
        return url;
    }

    function post(url, data, config){
        return $http.post(translateUrl(url), data, config);
    }
};

angular.module('portRoyalApp.backendGateway', [])
    .service('backendGateway', backendGateway);