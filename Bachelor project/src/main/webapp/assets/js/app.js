var app = angular.module('sdbApp', [
   'ngRoute' ,                                   
   'sdbApp.home',
   'sdbApp.userAdministration',
   'sdbApp.selecteduser',
   'sdbApp.editUser',
   'sdbApp.auditLog'
   ]).
   config(['$routeProvider', function($routeProvider) {
	   $routeProvider.otherwise({redirectTo: '/home'});
	 }]);


app.controller('mainCtrl', function($scope,$location,$http) {
	
	$location.path('/home');
    $scope.author = "Cech Jan";

    $scope.testVariable = 'test';

    var promise = $http({
        method : 'GET',
//        headers: { Accept: 'application/json' },
        url : '../port-royal/rest/test',
        responseType: 'text'
    })

    console.log(promise, 'primise');
    promise.then(function(response) {
        console.log(response, 'response');
        $scope.testVariable = response;
    }, function(response) {
        $scope.testVariable = 'error';
        console.log(response, 'response');
    });

    $scope.roles =  {
    	admin: false,
        business: false,
        user: false,
        };
    
    $http({
        method: 'GET',
        url: '/sdb/roles',
    }).then(function (r) {
        for (var i = r.data.length - 1; i >= 0; i--) {
            if (r.data[i] === 'ROLE_ADMINISTRATOR') {
            	$scope.roles.admin = true;
            } else if (r.data[i] === 'ROLE_BUSINESS') {
            	$scope.roles.business = true;
            } else if (r.data[i] === 'ROLE_USER') {
            	$scope.roles.user = true;
            }
        }
    })

    $scope.links = [
        {"title" : "Scoring Database FSD", "link": "http://ws3.ent.cgi.com/sites/023974/PragueOstrava%20315515/Internal%20Project%20I/Scoring%20Database%20FSD.docx"},
        {"title" : "Spring Documentation", "link": "http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/"},
        {"title" : "Best Practice Recommendations for Angular App Structure", "link": "https://docs.google.com/document/d/1XXMvReO8-Awi1EZXAXS4PzDzdNvV6pGcuaF4Q9821Es/pub"}
    ];
    
    
    
});
