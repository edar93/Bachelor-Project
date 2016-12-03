angular.module('sdbApp.editUser', [ 'ngRoute' ]).config(
		function($routeProvider) {
			$routeProvider.when('/edituser', {
				templateUrl : 'assets/views/edit user.html',
				controller : 'editUserController'
			})
		})
		.config(
		function($routeProvider) {
			$routeProvider.when('/edituser/:UserId', {
				templateUrl : 'assets/views/edit user.html',
				controller : 'editUserController'
			})
		})
.controller("editUserController", function($scope,$http,$location,selecteduser) {

	var isAdd = function (){
		return  (selecteduser.getUser()=== null);
	}
	
	if(isAdd()){
		$scope.newUsr = {
			name : "",
			surname : "",
			email : "",
			password : "",
			usrGroups: [],
			state : "A"
		}
	}else{
		$scope.newUsr = {
			name : selecteduser.getUser().name,
			surname : selecteduser.getUser().surname,
			email : selecteduser.getUser().email,
			password : selecteduser.getUser().password,
			state : selecteduser.getUser().state,
			usrGroups: [],
			id : selecteduser.getUser().id
		}
	}
	
	$scope.add = function() {
		var postUrl = isAdd() ? '/sdb/rest/users' : '/sdb/rest/users/' + selecteduser.getUser().id ;
			$http({
				method : 'POST',
				data : $scope.newUsr,
				url : postUrl
			}).then(function() {
				$location.path("/useradministration");
			}, function(r) {
				if (r.status === 500 || r.status === 400) {
					alert("Invalid input data");
					return;
				}
			});

	}
	
	$scope.cancel = function(){
		$location.path("/useradministration");
	}
	
	var loadGroups = function() {
		$http({
			method : 'GET',
			url : '/sdb/rest/groups',
		}).then(function(r) {
			if(! isAdd()){
				for (var i = r.data.length - 1; i >= 0; i--) {
					for(var j = selecteduser.getUser().usrGroups.length - 1; j >= 0; j-- ){
						if (r.data[i].ugId === selecteduser.getUser().usrGroups[j].ugId){
							$scope.newUsr.usrGroups.push(r.data[i]);
						}
					}
				}	
			}
				$scope.groupsList = r.data;	
			})
		};
		loadGroups();

		
		
});