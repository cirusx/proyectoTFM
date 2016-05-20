(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('authController',['$scope', '$http', '$location', '$cookies',
	                                 function ($scope, $http, $location, $cookies) {
		$scope.menuUser="";
		$scope.login = function() {
			var login = $scope.user.email;
			var password = $scope.user.password;

			$http.defaults.headers.common.Authorization = 'Basic '+btoa(login+':'+password);

			$http.get('http://localhost:8080/proyectoTFM/rest/users/'+login.replace('@', '%40')).then(function(user) {
				//alert(JSON.stringify(user));
				$cookies.put('user', login);
				$cookies.put('password', password);
				$scope.menuUser=true;
				$location.path("/");

			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})

			/*if ($cookies.get('user')) {
				$scope.menuUser=true;	
			}*/
		}


		$scope.logout = function() {
			if ($cookies.get('user')) {
				$cookies.remove("user"); 
				$cookies.remove("password");
				alert('logout');
				$location.path("/login");
				$scope.menuUser= false;
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

		if ($cookies.get('user')) {
			$scope.menuUser=true;	
		}
	}]);



}());