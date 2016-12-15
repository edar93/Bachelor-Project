'use strict'

 var  backendGateway = function($http){

    this.sayHello = function(){
        console.log('hello from backendGateway');
    };

    this.post = function(){
    }

};

angular.module('portRoyalApp.backendGateway', [])
    .service('backendGateway', backendGateway);