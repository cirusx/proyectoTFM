(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('authController',['$scope', '$rootScope', '$http', '$location', '$cookies',
	                                 function ($scope, $rootScope, $http, $location, $cookies) {
		
		$scope.login = function() {
			var login = $scope.user.email;
			var password = $scope.user.password;

			$http.defaults.headers.common.Authorization = 'Basic '+btoa(login+':'+password);

			$http.get('http://localhost:8080/proyectoTFM/rest/users/'+login.replace('@', '%40')).then(function(user) {
				$cookies.put('user', user.data.email);
				$cookies.put('password', user.data.password);
				$cookies.put('rol', user.data.userType);
				$rootScope.logged = true;
				$rootScope.loggedType = user.data.userType;
				$location.path("/");

			}, function(err) {
				console.error('ERR', err);
				alert("Usuario Incorrecto");
			})
		}


		$scope.logout = function() {
			if ($cookies.get('user')) {
				$cookies.remove("user"); 
				$cookies.remove("password");
				$cookies.remove("rol");
				alert('logout');
				$location.path("/login");
				$rootScope.logged = "";
				$rootScope.loggedType = "";
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