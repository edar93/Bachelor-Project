angular.module('sdbApp.userAdministration', [ 'ngRoute' ]).config(
		function($routeProvider) {
			$routeProvider.when('/useradministration', {
				templateUrl : 'assets/views/user administration.html',
				controller : 'userAdministrationController'
			})
		})
.controller("userAdministrationController", function($scope,$http,$location,selecteduser) {

		$scope.usersList = [];
		$scope.disabledOneSelected = true;
		$scope.disabledMoreSelected = true;
		$scope.selectAll = false;
		var methodtype = null;
		var countOfChangetRows;
		var changedRows;
				
		var loadUsers = function() {
		$http({
			method : 'GET',
			url : './rest/users',
		}).then(function(r) {
				$scope.usersList = r.data;
				for(var i=0;i < $scope.usersList.length; i++){
					$scope.usersList[i]
					.selected = false;
				}
			})
		};
		
		loadUsers();
		
		$scope.changed = function(){
			var coutOfSelected = 0;
			for(var i=0;i < $scope.usersList.length; i++){
				if ($scope.usersList[i].selected === true){
					coutOfSelected++;
				}
			}
				$scope.disabledOneSelected = true;
				$scope.disabledMoreSelected = true;
			if(coutOfSelected === 1){
				$scope.disabledOneSelected = false;
			}
			if(coutOfSelected > 0){
				$scope.disabledMoreSelected = false;
			}
		}
		
		$scope.predicate;
		 $scope.order = function(predicate) {
			    $scope.reverse = ($scope.predicate === predicate) ? !$scope.reverse : false;
			    $scope.predicate = predicate;
		 };
		 
		 var changeStatus = function(id){
				$http({
				    method : "PUT",
				    url : './rest/users/' + id,
				    data : methodtype
				}).then(function (){
					changedRows++;
					if(countOfChangetRows === changedRows){
						loadUsers(); 
						$scope.selectAll = false;
						$scope.changed();
					}
				});	
			}
		 
		 var changeAllSelected = function(){
			 countOfChangetRows = 0;
			 changedRows = 0;
				for(var i=0;i < $scope.usersList.length; i++){
					if($scope.usersList[i].selected === true){
						changeStatus($scope.usersList[i].id);
						countOfChangetRows++;
					}
				}
		 };
					
	$scope.changeStatus = function(newStatus){
		methodtype = {"method": newStatus};
		changeAllSelected();
	}	
	
	$scope.addUser = function(){
		selecteduser.setUser(null);
		$location.path("/edituser");
	}
	
	$scope.editUser = function(){
		for(var i=0;i < $scope.usersList.length; i++){
			if($scope.usersList[i].selected === true){
				selecteduser.setUser($scope.usersList[i]);
				$location.path("/edituser/" + $scope.usersList[i].id);
			}
		}	
	}
	
	$scope.selectAllUsrs = function(){
		for(var i=0;i < $scope.usersList.length; i++){
			$scope.usersList[i].selected = $scope.selectAll;
		}
		$scope.changed();
	}

});