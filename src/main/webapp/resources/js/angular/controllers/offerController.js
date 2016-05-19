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
			if ($cookies.get('user')) {
				alert("relogin user "+$cookies.get('user'));
				$http.defaults.headers.common.Authorization = 'Basic '+btoa($cookies.get('user')+':'+$cookies.get('password'));	
			}
			$http.get('http://localhost:8080/proyectoTFM/rest/users/'+$cookies.get('user').replace('@', '%40')).then(function(user) {
				alert(JSON.stringify(user));
				var userData = user.data;

				if($scope.offer.offerRegistrationList == undefined){
					$scope.offer.offerRegistrationList = [];
				}
				/*if(user.data.registerOfferList == undefined){
					user.data.registerOfferList = [];
				}
				user.data.registerOfferList.push($scope.offer);*/
				/*user.data.registerOfferList.push($scope.offer.offerId);*/
				$scope.offer.offerRegistrationList.push(userData);

				$http.put('http://localhost:8080/proyectoTFM/rest/offers/'+offerId, offer).then(
						function (response) {
							alert('registrado');
						},
						function (response) {

						}
				);
			}, function(err) {
				console.error('ERR', err);
				// err.status will contain the status code
			})
		}
	}]);
}());