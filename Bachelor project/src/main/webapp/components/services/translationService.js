'use strict'

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

    //    translationService.getDictionary('cz')
//        .then(function(dictionary){
//          $translateProvider
//            .translations('cz', dictionary)
//        });
};

angular.module('portRoyalApp.translationService', [])
    .service('translationService', translationService);
