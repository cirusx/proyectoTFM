(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('authController',['$scope', '$rootScope', '$http', '$location', '$cookies',
	                                 function ($scope, $rootScope, $http, $location, $cookies) {
		$rootScope.logged="";
		if ($cookies.get('user')) {
			$rootScope.logged=true;	
		}
		
		
		$scope.login = function() {
			var login = $scope.user.email;
			var password = $scope.user.password;

			$http.defaults.headers.common.Authorization = 'Basic '+btoa(login+':'+password);

			$http.get('http://localhost:8080/proyectoTFM/rest/users/'+login.replace('@', '%40')).then(function(user) {
				//alert(JSON.stringify(user));
				$cookies.put('user', login);
				$cookies.put('password', password);
				$rootScope.logged=true;
				$location.path("/");

			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		}


		$scope.logout = function() {
			if ($cookies.get('user')) {
				$cookies.remove("user"); 
				$cookies.remove("password");
				alert('logout');
				$location.path("/login");
				$rootScope.logged="";
			}
		}

		$scope.register = function() {
			alert(JSON.stringify($scope.user));
			$http.post('http://localhost:8080/proyectoTFM/rest/users/create', $scope.user).then(
					function (response) {

					},
					function (response) {

					}
			);
		}

	}]);



}());