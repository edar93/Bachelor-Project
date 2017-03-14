'use strict';

var administrationService = function (backendGateway) {

    this.initPage = initPage;

    var localScope;

    function initPage(scope) {
        localScope = scope;
        getPagesCount(scope);
        showPage(1);

        scope.currentPage = 1;
        scope.showPage = showPage;
    }

    function showPage(page){
        backendGateway.get('GET_ALL_ADMIN_USERS', false, false, 1)
            .then(function(response){
                localScope.usersList = response.data;
                console.log(response.data, 'response all users');
            });
    }
    
    function getPagesCount(scope){
        backendGateway.get('GET_PAGES_COUNT')
            .then(function(response){
                scope.pagesCount = createArray(response.data);
            })
    }

    function createArray (size) {
        var output = new Array();
        for (var i=0; i<size; i++) {
            output.push(i);
        }
        return output
    };

};
angular.module('portRoyalApp.administrationService', [])
    .service('administrationService', administrationService);