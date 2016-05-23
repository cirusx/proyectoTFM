(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('myOfferController',['$scope', '$http', '$location', '$routeParams', '$cookies',
	                                  function ($scope, $http, $location, $routeParams, $cookies) {

		//Get ID out of current URL
		var offerId = $scope.offerId = $routeParams.offerId;
		if (offerId != undefined) {
			$http.get('http://localhost:8080/proyectoTFM/rest/offers/'+ offerId).then(function(offer) {
				if (offer.data.offerId==null) {
					$location.path("/");
				}else {
					$http.get('http://localhost:8080/proyectoTFM/rest/offers/subcategories'+'?offerId='+offerId).then(function(subcategories) {
						offer.data.offerSubcategoryList = subcategories.data
					}, function(err) {
						console.error('ERR', err);
						// err.status will contain the status code
					});
					
					$http.get('http://localhost:8080/proyectoTFM/rest/offers/users'+'?offerId='+offerId).then(function(users) {
						offer.data.offerRegistrationList = users.data
						
						$scope.offerRegisterButton = 1;
						if ($cookies.get('user')) {
							var rol = $cookies.get('rol');
							if (rol == "1") {
								for (var user in users.data) {
									var email = users.data[user].email;
									var cookiemail= $cookies.get('user')
									if ( email == cookiemail){
										$scope.enableButton = 2;
									}
								}
							} else if($cookies.get('rol')== "2"){
								$scope.enableButton = 3;
							}
						} 
					}, function(err) {
						console.error('ERR', err);
						// err.status will contain the status code
					});
					
					
					//offer.data.
					$scope.offer = offer.data;
				}
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		}
	}]);
}());