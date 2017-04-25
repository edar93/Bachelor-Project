'use strict';

var administrationService = function (backendGateway, locationService) {

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
        scope.lockUser = lockUser;
        scope.showStats = showStats;
    }

    function showPage(page) {
        localScope.currentAdminPage = page;
        backendGateway.get('USER_ADMINISTRATION', false, false, page)
            .then(function (response) {
                localScope.usersList = response.data;
                addAdminParam(localScope.usersList);
            });
    }

    function showStats(login) {
        locationService.showPlayersStats(login);
    }

    function addAdminParam(list) {
        var roleAdmin = {role: 'ROLE_ADMINISTRATOR'};
        for (var user in list) {
            list[user].admin = false;
            for (var role in list[user].userRoleList) {
                if (list[user].userRoleList[role].role === roleAdmin.role) {
                    list[user].admin = true;
                }
            }
        }
    }

    function getPagesCount(scope) {
        backendGateway.get('GET_PAGES_COUNT')
            .then(function (response) {
                scope.pagesCount = createArray(response.data);
            })
    }

    function createArray(size) {
        var output = [];
        for (var i = 0; i < size; i++) {
            output.push(i);
        }
        return output
    }

    function resetPassword(login) {
        var urlSufix = login + '/RESET_PASSWORD';
        backendGateway.put('USER_ADMINISTRATION', undefined, undefined, true, false, urlSufix)
            .then(function (response) {
                showPage(localScope.currentAdminPage);
            })
    }

    function promoteToAdmin(login) {
        var urlSufix = login + '/GRANT_ADMIN_ROLE';
        backendGateway.put('USER_ADMINISTRATION', undefined, undefined, true, false, urlSufix)
            .then(function (response) {
                showPage(localScope.currentAdminPage);
            })
    }

    function lockUser(login, lock) {
        var urlSufix = login + '/LOCK' + lock;
        backendGateway.put('USER_ADMINISTRATION', undefined, undefined, true, false, urlSufix)
            .then(function (response) {
                showPage(localScope.currentAdminPage);
            })
    }

    function deleteAccount(login) {
        backendGateway.deleteHttp('USER_ADMINISTRATION', login)
            .then(function (response) {
                getPagesCount(localScope);
                showPage(localScope.currentAdminPage);
            })
    }

};
angular.module('portRoyalApp.administrationService', [])
    .service('administrationService', administrationService);