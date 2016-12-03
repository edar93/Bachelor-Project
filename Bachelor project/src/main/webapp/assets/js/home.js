angular.module('sdbApp.home', [ 'ngRoute' ]).config(
		function($routeProvider) {
			$routeProvider.when('/home', {
				templateUrl : 'assets/views/home.html',
				controller : 'homeController'
			})
		})
.controller("homeController", function() {

});