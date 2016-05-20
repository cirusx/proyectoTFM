(function () {
	'use strict';

	var app= angular.module('sgpfc');
	app.controller('offerController',['$scope', '$http', '$location', '$routeParams', '$cookies',
	                                  function ($scope, $http, $location, $routeParams, $cookies) {

		//Get ID out of current URL
		var offerId = $scope.offer_Id = $routeParams.offerId;
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
					var datetime = offer.data.offerTimeLimit;
					//var startOk = new Date(parseInt(datetime, 10));
					$scope.offer = offer.data;
				}
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		}

		$scope.offerUserRegister = function() {
			var offer = $scope.offer;
			var login = false;
			if ($cookies.get('user')) {
				//alert("relogin user "+$cookies.get('user'));
				var login = true
				//$http.defaults.headers.common.Authorization = 'Basic '+btoa($cookies.get('user')+':'+$cookies.get('password'));	
			} else {
				alert("No puedes registrarte ya que no estas registrado como alumno o logeado");
			}
			if(login) {
					//alert(JSON.stringify(user));
											
					$http.post('http://localhost:8080/proyectoTFM/rest/offers/'+offer.offerId+'/students').then(
							function (response) {
								alert('registrado');
							},
							function (response) {
								alert('error ene el registro');
							}
					);
				

			
			}

		}
	}]);
}());