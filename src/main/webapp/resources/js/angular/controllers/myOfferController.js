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

					if ($cookies.get('user')) {
						var rol = $cookies.get('rol');
						if (rol == "2") {
							$scope.registrations = true;
							$http.get('http://localhost:8080/proyectoTFM/rest/offers/users'+'?offerId='+offerId).then(function(users) {
								offer.data.offerRegistrationList = users.data	
							}, function(err) {
								console.error('ERR', err);
								// err.status will contain the status code
							});
						}
					}
					
					var offerTimeLimit = offer.data.offerTimeLimit;
					var actualDate = Date.now();
					
					if (actualDate >= offerTimeLimit){
						offer.data.offerClose = true;
						$scope.offer = offer.data;
						$http.put('http://localhost:8080/proyectoTFM/rest/offers/'+offerId+'/close').then(function(cerrar) {
							$scope.offerClose = true;
						}, function(err) {
							console.error('ERR', err);
							// err.status will contain the status code
						});
					}
					
					//offer.data.
					$scope.offer = offer.data;
				}
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		}
		
		$scope.closeOffer = function() {
			$http.put('http://localhost:8080/proyectoTFM/rest/offers/'+offerId+'/close').then(function(cerrar) {
				$scope.offer.offerClose = true;
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			});
		}
		
	}]);
}());