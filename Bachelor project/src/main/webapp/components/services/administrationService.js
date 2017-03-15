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
        scope.resetPassword = resetPassword;
        scope.promoteToAdmin = promoteToAdmin;
        scope.deleteAccount = deleteAccount;
    }

    function showPage(page){
        backendGateway.get('USER_ADMINISTRATION', false, false, 1)
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

    function resetPassword(login) {
        var urlSufix = login + '/RESET_PASSWORD';
        backendGateway.put('USER_ADMINISTRATION', undefined, undefined, true, false, urlSufix)
            .then(function (response) {
                showPage(localScope.currentPage);
            })
    }

    function promoteToAdmin(login) {
        var urlSufix = login + '/GRANT_ADMIN_ROLE';
        backendGateway.put('USER_ADMINISTRATION', undefined, undefined, true, false, urlSufix)
            .then(function (response) {
                showPage(localScope.currentPage);
            })
    }

    function deleteAccount(login) {

    }

};
angular.module('portRoyalApp.administrationService', [])
    .service('administrationService', administrationService);