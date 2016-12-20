'use strict'

// never used
var translationService = function($translate, backendGateway){

    this.getDictionary = getDictionary;

    function getDictionary(language){
        return backendGateway.get('DICTIONARY_' + language.toUpperCase())
            .then(function(response){
                console.log(response, 'succ');
                return response.data;
            }, function(response){
               console.log(response, 'err');
        })
    }
};

angular.module('portRoyalApp.translationService', [])
    .service('translationService', translationService);
