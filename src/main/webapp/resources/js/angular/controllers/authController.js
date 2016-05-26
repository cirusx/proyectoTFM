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
				$scope.incorrectUser= false;
				if (user.data.enable == true) {
					$cookies.put('user', user.data.email);
					$cookies.put('password', password);
					$cookies.put('rol', user.data.userType);
					$rootScope.logged = true;
					$scope.enableUser = 1;
					$rootScope.loggedType = user.data.userType;
					$location.path("/");
				} else {
					$scope.enableUser = 0;
				}


			}, function(err) {
				console.error('ERR', err);
				$scope.incorrectUser= true;
			})
		}

		$scope.logout = function() {
			if ($cookies.get('user')) {
				$cookies.remove("user"); 
				$cookies.remove("password");
				$cookies.remove("rol");
				$location.path("/login");
				$rootScope.logged = "";
				$rootScope.loggedType = "";
			}
		}

		$scope.register = function() {
			$scope.emailUsed = false;
			$scope.userCreated = false;
			var email = $scope.user.email;
			$http.get('http://localhost:8080/proyectoTFM/rest/users/check'+'?email='+email.replace('@', '%40')).then(function(user) {

				$scope.emailUsed = true;
				$scope.userCreated = false;

			}, function(user) {

				$scope.emailUsed = false;

				$http.post('http://localhost:8080/proyectoTFM/rest/users', $scope.user).then(function (response) {
					$scope.userCreated = true;
					$scope.emailUsed = false;
				},
				function (response) {
					$scope.userCreated = false;
				})
			})
		}	
	}]);
}());