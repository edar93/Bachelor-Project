angular.module('sdbApp.auditLog', [ 'ngRoute']).config(
		function($routeProvider) {
			$routeProvider.when('/auditlog', {
				templateUrl : 'assets/views/audit log.html',
				controller : 'auditLogController'
			})
		})
.controller("auditLogController", function($scope,$http) {

	$scope.logList;
	$scope.count;
	$scope.maxSize = 10;
	$scope.currentPage= 1;
	
	var loadLogs = function() {
		$http({
			method : 'GET',
			url : '/sdb/rest/auditlog',
		}).then(function(r) {
				$scope.logList = r.data;	
			})
			
		$http({
			method : 'GET',
			url : '/sdb/rest/auditlog/count',
		}).then(function(r) {
				$scope.count = r.data;	
			})
		};
		
		loadLogs();

});