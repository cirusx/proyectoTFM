(function () {
    'use strict';
 
    var app= angular.module('sgpfc');
    app.controller('authController',['$scope', '$http', '$location', '$cookies',
	                                  function ($scope, $http, $location, $cookies) {
		
	$scope.login = function() {
		var login = $scope.user.email;
		var password = $scope.user.password;
		
		$http.defaults.headers.common.Authorization = 'Basic '+btoa(login+':'+password);
		
		$http.get('http://localhost:8080/proyectoTFM/rest/users/'+login.replace('@', '%40')).then(function(user) {
			//alert(JSON.stringify(user));
			$cookies.put('user', login);
			$cookies.put('password', password);
			
			$location.path("/");
			
		}, function(err) {
		    console.error('ERR', err);
		    // err.status will contain the status code
		})
		
		if ($cookies.get('user')) {
			$scope.menuUser=true;	
		}
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
	
	app.controller('createOfferController',['$scope', '$http', '$location', '$cookies',
	                                  function ($scope, $http, $location, $cookies) {
	
		$scope.createOffer = function() {
			alert(JSON.stringify($scope.offer));
			
			var datetimeStr1 = "2020/07/19 07:00";
			var datetimeStr2 = "2020/07/19 08:00";
			var datetime;
			
			if($scope.offer.offerWithLimit == 0) {
				$scope.offer.offerWithLimit = false;
			}else{
				$scope.offer.offerWithLimit = true;
			}
			if($scope.offer.offerTimeLimit == 1) {
				datetime = new Date(datetimeStr1);
				$scope.offer.offerTimeLimit = datetime;
			}else if($scope.offer.offerTimeLimit == 2){
				datetime = new Date(datetimeStr2);
				$scope.offer.offerTimeLimit = datetime;
			}else{
				$scope.offer.offerTimeLimit = null;
			}
			
			alert(JSON.stringify($scope.offer));
			$http.post('http://localhost:8080/proyectoTFM/rest/offers/create', $scope.offer).then(
					function (response) {
						alert("La Oferta ha sido creada correctamente");
					},
					function (response) {
						alert("La Oferta no se ha podido crear correctamente");
					}
			);
		}
		
	}]);
 
}());